package com.common.util.zookeeper;

import com.alibaba.fastjson.JSONObject;
import com.common.callback.IZkConnectionListenerCallback;
import com.common.entity.ZkConnectionNodeEntity;
import com.common.util.balance.RoundRobin;
import com.common.util.enumeration.EnumerationUtil;
import com.common.util.ketama.ConsistentHashing;
import com.common.enumeration.ServerStateEnum;
import com.common.enumeration.ServiceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by madali on 2017/4/27.
 */
final class ZkConnectionNodeListener extends ZkNodeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkConnectionNodeListener.class);

    private final Map<String, ZkConnectionNodeEntity> connectionMap = new ConcurrentHashMap<>();

//    private final int virtualNodeCount = 160;

    private final RoundRobin roundRobin;
    private final boolean roundRobinFlag;

    private final ConsistentHashing<String> consistentHashing;
    private final boolean consistentHashingFlag;

    private final IZkConnectionListenerCallback listenerCallback;

    private final ServiceEnum serviceEnum;

    protected ZkConnectionNodeListener(String path, IZkConnectionListenerCallback listenerCallback) {
        super(path);

        ServiceEnum serviceEnum = null;

        ServiceEnum[] serviceEnums = ServiceEnum.values();

        for (ServiceEnum se : serviceEnums) {

            if (super.getNodeName().equals(se.getZookeeperNodeName())) {
                serviceEnum = se;

                break;
            }
        }

        this.serviceEnum = serviceEnum;
        this.listenerCallback = listenerCallback;

        if (serviceEnum == null) {

            LOGGER.warn("Service (path: {}) is not existed.", path);

            this.consistentHashing = null;
            this.consistentHashingFlag = false;

            this.roundRobin = null;
            this.roundRobinFlag = false;

            return;
        }

        switch (this.serviceEnum) {

            case RouterService:

                //一致性哈希
                this.consistentHashing = new ConsistentHashing<>();
//                this.consistentHashing = new ConsistentHashing<>(this.virtualNodeCount);
                this.consistentHashingFlag = true;

                this.roundRobin = null;
                this.roundRobinFlag = false;

                break;

            case CustomerService:

                this.consistentHashing = null;
                this.consistentHashingFlag = false;

                //负载均衡
                this.roundRobin = new RoundRobin();
                this.roundRobinFlag = true;

                break;
            default:

                this.consistentHashing = null;
                this.consistentHashingFlag = false;

                this.roundRobin = null;
                this.roundRobinFlag = false;

                break;
        }
    }

    protected ZkConnectionNodeListener(String path) {
        this(path, null);
    }

    /**
     * 添加子节点
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     */
    @Override
    public void onChildAdd(String nodeName, String nodeValue) {

        ZkConnectionNodeEntity connectionNodeEntity = this.getConnectionNodeEntity(nodeName, nodeValue);

        if (this.consistentHashing != null) {
            try {
                //add
                this.consistentHashing.addNode(nodeName);
                LOGGER.info("add hash node: " + nodeName);
            } catch (Throwable t) {
                LOGGER.error(t.getMessage(), t);
            }
        } else if (this.roundRobin != null) {

            if (ServerStateEnum.Running == connectionNodeEntity.getServerStateEnum()) {

                this.roundRobin.addNode(nodeName);
                LOGGER.info("add roundRobin node: " + nodeName);
            } else {
                this.roundRobin.removeNode(nodeName);
                LOGGER.info("remove roundRobin node: " + nodeName);
            }
        }

        this.updateConnection(nodeName, nodeValue);

        LOGGER.info("add connection: " + super.getNodeName() + " -> " + nodeName);

        if (this.listenerCallback != null)
            this.listenerCallback.onAdd(this.serviceEnum, connectionNodeEntity);
    }

    /**
     * 修改子节点
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     */
    @Override
    public void onChildUpdate(String nodeName, String nodeValue) {

        ZkConnectionNodeEntity connectionNodeEntity = this.getConnectionNodeEntity(nodeName, nodeValue);

        if (this.roundRobin != null) {

            if (ServerStateEnum.Running == connectionNodeEntity.getServerStateEnum()) {

                this.roundRobin.addNode(nodeName);
                LOGGER.info("add roundRobin node: " + nodeName);
            } else {
                this.roundRobin.removeNode(nodeName);
                LOGGER.info("remove roundRobin node: " + nodeName);
            }
        }

        this.updateConnection(nodeName, nodeValue);

        LOGGER.info("update connection: " + super.getNodeName() + " -> " + nodeName);

        if (this.listenerCallback != null)
            this.listenerCallback.onUpdate(this.serviceEnum, connectionNodeEntity);
    }

    /**
     * 删除子节点
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     */
    @Override
    public void onChildRemove(String nodeName, String nodeValue) {

        ZkConnectionNodeEntity connectionNodeEntity = this.getConnectionNodeEntity(nodeName, nodeValue);

        if (this.consistentHashing != null) {
            try {
                this.consistentHashing.removeNode(nodeName);
                LOGGER.info("remove hash node: " + nodeName);
            } catch (Throwable t) {
                LOGGER.error(t.getMessage(), t);
            }
        } else if (this.roundRobin != null) {
            this.roundRobin.removeNode(nodeName);
            LOGGER.info("remove roundRobin node: " + nodeName);
        }

        this.connectionMap.remove(nodeName);

        LOGGER.info("remove connection: " + super.getNodeName() + " -> " + nodeName);

        if (this.listenerCallback != null)
            this.listenerCallback.onRemove(this.serviceEnum, connectionNodeEntity);
    }

    /**
     * 更新zookeeper本地缓存中的节点
     *
     * @param nodeName  zookeeper节点名字
     * @param nodeValue zookeeper节点值
     */
    public void updateConnection(String nodeName, String nodeValue) {

        ZkConnectionNodeEntity connectionNodeEntity = this.getConnectionNodeEntity(nodeName, nodeValue);

        this.connectionMap.put(nodeName, connectionNodeEntity);
    }

    /**
     * 获取服务连接
     *
     * @return 服务连接
     */
    public ZkConnectionNodeEntity getConnection() {

        ZkConnectionNodeEntity connectionNodeEntity = this.getConn();

        if (connectionNodeEntity == null) {
            //缓存中不存在时，从zookeeper中读取
            try {
                initConnectionMap();

                connectionNodeEntity = this.getConn();

                if (connectionNodeEntity == null)
                    LOGGER.debug("No connection in {}.", super.getPath());

            } catch (Throwable t) {
                LOGGER.warn(t.getMessage());
            }
        }

        return connectionNodeEntity;
    }

    /**
     * 获取服务连接（一致性哈希）
     *
     * @param key 服务配置key
     * @return 服务连接
     */
    public ZkConnectionNodeEntity getConnection(String key) {

        ZkConnectionNodeEntity connectionNodeEntity;

        if (this.consistentHashingFlag) {

            connectionNodeEntity = this.getConn(key);

            if (connectionNodeEntity == null) {
                //缓存中不存在时，从zookeeper中读取
                try {
                    initConnectionMap();

                    connectionNodeEntity = this.getConn(key);

                    if (connectionNodeEntity == null)
                        LOGGER.debug("No connection in {} with key {}.", super.getPath(), key);
                } catch (Throwable t) {
                    LOGGER.warn(t.getMessage());
                }
            }
        } else
            connectionNodeEntity = this.getConnection();

        return connectionNodeEntity;
    }

    /**
     * 所有的Running状态的服务列表
     *
     * @return 所有的服务连接
     */
    public List<ZkConnectionNodeEntity> listConnection() {

        List<ZkConnectionNodeEntity> runningConnNodeEntityList = this.listConn();

        if (runningConnNodeEntityList == null || runningConnNodeEntityList.size() == 0) {
            //缓存中不存在时，从zookeeper中读取
            try {
                initConnectionMap();

                runningConnNodeEntityList = this.listConn();

                if (runningConnNodeEntityList == null || runningConnNodeEntityList.size() == 0)
                    LOGGER.debug("No connection in {}.", super.getPath());
            } catch (Throwable t) {
                LOGGER.warn(t.getMessage());
            }
        }

        return runningConnNodeEntityList;
    }

    //getConnection的基础方法
    private ZkConnectionNodeEntity getConn() {

        ZkConnectionNodeEntity connectionNodeEntity = null;

        List<ZkConnectionNodeEntity> connectionNodeEntities = new ArrayList<>(this.connectionMap.values());

        if (connectionNodeEntities.size() == 0)
            return connectionNodeEntity;

        if (connectionNodeEntities.size() == 1 || !this.roundRobinFlag) {
            //获取第一个
            for (ZkConnectionNodeEntity zkConnectionNodeEntity : connectionNodeEntities) {

                if (ServerStateEnum.Running == zkConnectionNodeEntity.getServerStateEnum()) {
                    connectionNodeEntity = zkConnectionNodeEntity;

                    break;
                }
            }
        } else if (connectionNodeEntities.size() > 1) {
            //负载均衡机制
            String nodeName = this.roundRobin.next();
            if (nodeName != null)
                connectionNodeEntity = this.connectionMap.get(nodeName);
        }

        return connectionNodeEntity;
    }

    //getConnection(String)的基础方法
    private ZkConnectionNodeEntity getConn(String key) {

        String nodeName = this.consistentHashing.getNode(key);

        if (ServerStateEnum.Running == this.connectionMap.get(nodeName).getServerStateEnum()) {
            return this.connectionMap.get(nodeName);
        } else {
            return null;
        }

    }

    //listConnection的基础方法
    private List<ZkConnectionNodeEntity> listConn() {

        //所有的服务列表
        List<ZkConnectionNodeEntity> connectionNodeEntityList;

        //所有的Running状态的服务列表
        List<ZkConnectionNodeEntity> runningConnNodeEntityList = new ArrayList<>();

        try {

            connectionNodeEntityList = new ArrayList<>(this.connectionMap.values());

            for (ZkConnectionNodeEntity zkConnectionNodeEntity : connectionNodeEntityList) {

                if (ServerStateEnum.Running == zkConnectionNodeEntity.getServerStateEnum())
                    runningConnNodeEntityList.add(zkConnectionNodeEntity);

            }

        } catch (Throwable t) {
            runningConnNodeEntityList = null;
        }

        return runningConnNodeEntityList;
    }

    //初始化缓存
    private void initConnectionMap() throws Exception {
        List<Map.Entry<String, String>> entryList = ZkUtil.listNode(super.getPath());

        for (Map.Entry<String, String> entry : entryList)

            this.onChildAdd(entry.getKey().substring(super.getPath().length() + 1), entry.getValue());
    }

    /**
     * 根据节点的nodeName和nodeValue获取节点的服务连接
     *
     * @param nodeName
     * @param nodeValue
     * @return 服务连接
     */
    protected ZkConnectionNodeEntity getConnectionNodeEntity(String nodeName, String nodeValue) {

        JSONObject jsonObject = JSONObject.parseObject(nodeValue);

        String id = nodeName;
        String ip = jsonObject.getString("Ip");
        int port = jsonObject.getIntValue("Port");
        ServerStateEnum serverStateEnum = EnumerationUtil.valueOf(ServerStateEnum.class, jsonObject.getIntValue("State"));

        return new ZkConnectionNodeEntity(id, ip, port, serverStateEnum);
    }
}
