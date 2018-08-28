package com.mada.common.util.zookeeper;

import com.alibaba.fastjson.JSONObject;
import com.mada.common.util.zookeeper.callback.IZkConnectionListenerCallback;
import com.mada.common.util.zookeeper.callback.IZkInfrastructureListenerCallback;
import com.mada.common.util.zookeeper.callback.IZkServiceConfigListenerCallback;
import com.mada.common.util.zookeeper.entity.ZkConfigurationNodeEntity;
import com.mada.common.util.zookeeper.entity.ZkConnectionNodeEntity;
import com.mada.common.util.configuration.ConfigurationUtil;
import com.mada.common.enumeration.InfrastructureEnum;
import com.mada.common.enumeration.ServerStateEnum;
import com.mada.common.enumeration.ServiceEnum;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by madali on 2017/4/27.
 */
public class ZkUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkUtil.class);

    private static final String NAMESPACE = "dm2";

    //服务配置
    private static final String SERVICE_CONFIG_PATH = "/serviceConfig";
    //服务连接
    private static final String INFRASTRUCTURE_PATH = "/infrastructure";
    //基础服务配置
    private static final String CONNECTION_PATH = "/connected";

    public static final String ZOOKEEPER_HOST;

    protected static final CuratorFramework client;

    private static final Map<String, ZkConfigurationNodeListener> SERVICE_CONFIG_MAP = new ConcurrentHashMap<>();
    private static final Map<String, ZkConfigurationNodeListener> INFRASTRUCTURE_MAP = new ConcurrentHashMap<>();
    private static final Map<String, ZkConnectionNodeListener> CONNECTION_MAP = new ConcurrentHashMap<>();

    private static ZkInitializationData initializationData = new ZkInitializationData();

    private static ServiceEnum currentService = null;
    private static ServiceEnum[] dependentServices = null;

    private static boolean initFlag = false;

    static {
        ZOOKEEPER_HOST = ConfigurationUtil.ZOOKEEPER_HOST;

        client = CuratorFrameworkFactory.builder()
                .connectString(ZOOKEEPER_HOST)
                .namespace(NAMESPACE)
                .retryPolicy(new RetryNTimes(3, 1000))
                .build();
    }

    /**
     * 初始化方法
     *
     * @param initData zk初始化数据
     */
    public static void init(ZkInitializationData initData) {

        if (!initFlag) {
            initializationData = initData;
            initFlag = true;
        }
    }

    /**
     * 获取当前的服务枚举
     *
     * @return
     */
    public static ServiceEnum getCurrentService() {
        return currentService;
    }

    public static void connect(ServiceEnum curServ, ServiceEnum[] depServs) {

        if (currentService == null) {
            if (curServ != null) {
                currentService = curServ;
            }

            if (dependentServices == null && depServs != null && depServs.length != 0)
                dependentServices = depServs;

            //监听必须在client.start()方法之前启动才生效
            //服务配置监听
            listenNode(SERVICE_CONFIG_PATH, ZkConfigurationNodeListener.class);

            //基础服务配置监听
            listenNode(INFRASTRUCTURE_PATH, ZkConfigurationNodeListener.class);

            //服务连接监听
            listenNode(CONNECTION_PATH, ZkConnectionNodeListener.class);

            client.start();

            LOGGER.info("Connect to zookeeper (host: {}).", ZOOKEEPER_HOST);

            while (true) {

                //保证所有监听器已启动
                if (SERVICE_CONFIG_MAP.size() >= ServiceEnum.values().length &&
                        INFRASTRUCTURE_MAP.size() >= InfrastructureEnum.values().length &&
                        CONNECTION_MAP.size() >= ServiceEnum.values().length)
                    break;

                try {
                    LOGGER.info("Waiting for all listeners started.");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 连接Zookeeper
     */
    public static void connect(ServiceEnum curSer) {

        connect(curSer, null);
    }

    /**
     * 断开Zookeeper
     */
    public static void disconnect() {

        if (currentService != null) {

            currentService = null;

            client.close();

            LOGGER.info("Disconnect zookeeper (host: {}).", ZOOKEEPER_HOST);
        }
    }

    private static void addNode(Map.Entry<String, String> entry) throws Exception {

        client.create().withMode(CreateMode.EPHEMERAL).forPath(entry.getKey(), entry.getValue().getBytes("UTF-8"));
    }

    protected static void deleteNode(String path) throws Exception {

        client.delete().forPath(path);
    }

    private static void updateNode(Map.Entry<String, String> entry) throws Exception {

        client.setData().forPath(entry.getKey(), entry.getValue().getBytes("UTF-8"));
    }

    protected static Map.Entry<String, String> getNode(String path) {
        Map.Entry<String, String> entry;

        try {
            byte[] data = client.getData().forPath(path);
            entry = new AbstractMap.SimpleEntry<>(path, new String(data, "UTF-8"));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return entry;
    }

    protected static List<Map.Entry<String, String>> listNode(String path) {
        List<Map.Entry<String, String>> entryList = new ArrayList<>();

        try {
            List<String> list = client.getChildren().forPath(path);
            for (int i = 0; i < list.size(); i++) {
                String childPath = path + "/" + list.get(i);
                Map.Entry<String, String> entry = getNode(childPath);
                entryList.add(entry);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return entryList;
    }

    protected static boolean checkNode(String path) throws Exception {

        return client.checkExists().forPath(path) != null;
    }

    private static void listenChildNode(IZkNodeListener zkNodeListener) {

        String childPath = zkNodeListener.getPath();

        PathChildrenCache cache = new PathChildrenCache(client, childPath, true);

        cache.getListenable().addListener((client, event) -> {

            ChildData node = event.getData();
            if (node == null) {
                //表示与zk断开连接
                cache.close();

                return;
            }

            String nodeName = node.getPath().substring(childPath.length() + 1);
            String nodeValue = new String(node.getData(), "UTF-8");
//            System.out.println(event.getType().name() + ": " + childPath + "/" + nodeName);
            switch (event.getType()) {
                case CHILD_ADDED:
                    zkNodeListener.onChildAdd(nodeName, nodeValue);
                    break;
                case CHILD_UPDATED:
                    zkNodeListener.onChildUpdate(nodeName, nodeValue);
                    break;
                case CHILD_REMOVED:
                    zkNodeListener.onChildRemove(nodeName, nodeValue);
                    break;
            }
        });

        try {
            cache.start();

            LOGGER.info("Listen successfully (path = {}).", childPath);
        } catch (Throwable t) {
            LOGGER.error("Listen failed (path = \"" + childPath + "\").", t);
        }
    }

    private static void listenNode(final String path, final Class<? extends IZkNodeListener> zkNodeListenerClass) {
        final Listenable<ConnectionStateListener> connectionStateListenable = client.getConnectionStateListenable();

        connectionStateListenable.addListener((client, state) -> {

            //CONNECTED -> SUSPENDED -> LOST -> RECONNECTED

            switch (state) {
                case RECONNECTED:

                    connected(path, zkNodeListenerClass);

                    if (currentService != null) {
                        try {
                            String connectionPath = CONNECTION_PATH + "/" + currentService.getZookeeperNodeName() + "/" + ConfigurationUtil.getServerId();

                            if (!checkNode(connectionPath)) {
                                //重连zk后更新服务状态
                                addConnection(currentService, ServerStateEnum.Running);
                            }
                        } catch (Throwable t) {
                            LOGGER.warn(t.getMessage(), t);
                        }
                    }

                    break;
                case CONNECTED:

                    connected(path, zkNodeListenerClass);

                    break;
//                case SUSPENDED:
                case LOST:

                    //回收机制
                    disconnected(path);

                    break;
            }
        });
    }

    private static void connected(String path, final Class<? extends IZkNodeListener> zkNodeListenerClass) {

        try {
            Constructor<? extends IZkNodeListener> constructor = null;

            switch (path) {
                case SERVICE_CONFIG_PATH:

                    if (initializationData.getServiceConfigListenerCallback() != null) {
                        //注入应用层面的事件处理机制
                        constructor = zkNodeListenerClass.getDeclaredConstructor(String.class, IZkServiceConfigListenerCallback.class);
                    }

                    break;
                case INFRASTRUCTURE_PATH:

                    if (initializationData.getInfrastructureListenerCallback() != null) {
                        //注入应用层面的事件处理机制
                        constructor = zkNodeListenerClass.getDeclaredConstructor(String.class, IZkInfrastructureListenerCallback.class);
                    }

                    break;
                case CONNECTION_PATH:

                    if (initializationData.getConnectionListenerCallback() != null) {
                        //注入应用层面的事件处理机制
                        constructor = zkNodeListenerClass.getDeclaredConstructor(String.class, IZkConnectionListenerCallback.class);
                    }

                    break;
            }

            if (constructor == null)
                constructor = zkNodeListenerClass.getDeclaredConstructor(String.class);

            List<Map.Entry<String, String>> entryList = listNode(path);

            for (Map.Entry<String, String> entry : entryList) {

                String childPath = entry.getKey();

                IZkNodeListener zkNodeListener = null;

                switch (path) {
                    case SERVICE_CONFIG_PATH:

                        if (initializationData.getServiceConfigListenerCallback() != null) {
                            //注入应用层面的事件处理机制
                            zkNodeListener = constructor.newInstance(childPath, initializationData.getServiceConfigListenerCallback());
                        }

                        break;
                    case INFRASTRUCTURE_PATH:

                        if (initializationData.getInfrastructureListenerCallback() != null) {
                            //注入应用层面的事件处理机制
                            zkNodeListener = constructor.newInstance(childPath, initializationData.getInfrastructureListenerCallback());
                        }

                        break;
                    case CONNECTION_PATH:

                        if (initializationData.getConnectionListenerCallback() != null) {
                            //注入应用层面的事件处理机制
                            zkNodeListener = constructor.newInstance(childPath, initializationData.getConnectionListenerCallback());
                        }

                        break;
                }

                if (zkNodeListener == null)
                    zkNodeListener = constructor.newInstance(childPath);

                listenChildNode(zkNodeListener);

                switch (path) {
                    case SERVICE_CONFIG_PATH:
                        SERVICE_CONFIG_MAP.put(childPath, (ZkConfigurationNodeListener) zkNodeListener);
                        break;
                    case INFRASTRUCTURE_PATH:
                        INFRASTRUCTURE_MAP.put(childPath, (ZkConfigurationNodeListener) zkNodeListener);
                        break;
                    case CONNECTION_PATH:
                        CONNECTION_MAP.put(childPath, (ZkConnectionNodeListener) zkNodeListener);
                        break;
                }
            }
        } catch (Throwable t) {

            throw new RuntimeException(t);
        }
    }

    private static void disconnected(String path) {

        switch (path) {
            case SERVICE_CONFIG_PATH:
                SERVICE_CONFIG_MAP.clear();
                break;
            case INFRASTRUCTURE_PATH:
                INFRASTRUCTURE_MAP.clear();
                break;
            case CONNECTION_PATH:
                CONNECTION_MAP.clear();
                break;
        }
    }

    private static void addConnection(ServiceEnum serviceEnum, ServerStateEnum serverStateEnum) {

        String path = CONNECTION_PATH + "/" + serviceEnum.getZookeeperNodeName() + "/" + ConfigurationUtil.getServerId();

        while (true) {
            try {
                if (!checkNode(path)) {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Ip", ConfigurationUtil.getServerIp());
                    jsonObject.put("Port", ConfigurationUtil.SERVER_PORT);
                    jsonObject.put("State", serverStateEnum.value());

                    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(path, jsonObject.toJSONString());

                    addNode(entry);

                    break;
                }

                LOGGER.info("/" + NAMESPACE + path + " service is already exists! Please wait for 5 seconds and retry.");

                Thread.sleep(5000);

            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    }

    /**
     * 服务注册
     *
     * @param serverStateEnum 负载管理状态枚举
     */
    public static void addConnection(ServerStateEnum serverStateEnum) {

        addConnection(currentService, serverStateEnum);
    }

    private static void deleteConnection(ServiceEnum serviceEnum) {

        String path = CONNECTION_PATH + "/" + serviceEnum.getZookeeperNodeName() + "/" + ConfigurationUtil.getServerId();

        try {
            if (checkNode(path))
                deleteNode(path);
            else {
                //获取节点，没有时，忽略
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

    }

    /**
     * 服务删除
     */
    public static void deleteConnection() {

        deleteConnection(currentService);
    }

    private static void updateConnection(ServiceEnum serviceEnum, ServerStateEnum serverStateEnum) {

        String path = CONNECTION_PATH + "/" + serviceEnum.getZookeeperNodeName();

        String childPath = path + "/" + ConfigurationUtil.getServerId();

        try {
            if (checkNode(childPath)) {
                Map.Entry<String, String> entry = getNode(childPath);
                JSONObject jsonObject = JSONObject.parseObject(entry.getValue());
                jsonObject.put("State", serverStateEnum.value());

                entry = new AbstractMap.SimpleEntry<>(childPath, jsonObject.toJSONString());
                updateNode(entry);

                //实时更新缓存，解决zookeeper延迟同步本地缓存
                CONNECTION_MAP.get(path).updateConnection(ConfigurationUtil.getServerId(), entry.getValue());
            } else {
                //获取节点，没有时，抛异常
                throw new RuntimeException("There is no zookeeper node named \"" + childPath + "\".");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 服务更新
     *
     * @param serverStateEnum 负载管理状态枚举
     */
    public static void updateConnection(ServerStateEnum serverStateEnum) {

        updateConnection(currentService, serverStateEnum);
    }

    /**
     * 服务发现（负载均衡或获取第一个）
     *
     * @param serviceEnum 服务枚举
     * @return 服务连接实体
     */
    public static ZkConnectionNodeEntity getConnection(ServiceEnum serviceEnum) {

        String path = CONNECTION_PATH + "/" + serviceEnum.getZookeeperNodeName();

        ZkConnectionNodeListener listener = CONNECTION_MAP.get(path);

        return listener == null ? null : listener.getConnection();
    }

    /**
     * 服务发现（一致性哈希）
     *
     * @param serviceEnum 服务枚举
     * @param key         服务配置key
     * @return 服务连接实体
     */
    public static ZkConnectionNodeEntity getConnection(ServiceEnum serviceEnum, String key) {

        String path = CONNECTION_PATH + "/" + serviceEnum.getZookeeperNodeName();

        ZkConnectionNodeListener listener = CONNECTION_MAP.get(path);

        return listener == null ? null : listener.getConnection(key);
    }

    /**
     * 服务发现（CustomerService）
     *
     * @param nodeName 节点名字
     * @return 服务连接实体
     */
    public static ZkConnectionNodeEntity getCustomerConnection(String nodeName) {

        String path = CONNECTION_PATH + "/" + ServiceEnum.CustomerService.getZookeeperNodeName();

        return getConn(nodeName, path);
    }

    //服务发现（CustomerService）基础方法
    private static ZkConnectionNodeEntity getConn(String nodeName, String path) {

        String nodeValue = getNode(path + "/" + nodeName).getValue();

        ZkConnectionNodeListener listener = CONNECTION_MAP.get(path);

        return listener == null ? null : listener.getConnectionNodeEntity(nodeName, nodeValue);
    }

    /**
     * 服务发现（获取所有的服务）
     *
     * @param serviceEnum 服务枚举
     * @return 所有的服务
     */
    public static List<ZkConnectionNodeEntity> listConnection(ServiceEnum serviceEnum) {

        String path = CONNECTION_PATH + "/" + serviceEnum.getZookeeperNodeName();

        ZkConnectionNodeListener listener = CONNECTION_MAP.get(path);

        return listener == null ? null : listener.listConnection();
    }

    /**
     * 服务依赖
     */
    public static void dependConnection() {

        if (dependentServices != null) {

            for (ServiceEnum dependentService : dependentServices) {

                while (true) {

                    boolean dependentFlag = false;

                    List<ZkConnectionNodeEntity> connectionNodeEntities = listConnection(dependentService);

                    if (connectionNodeEntities != null) {

                        for (ZkConnectionNodeEntity connectionNodeEntity : connectionNodeEntities) {

                            if (ServerStateEnum.Running == connectionNodeEntity.getServerStateEnum()) {
                                dependentFlag = true;
                                break;
                            }
                        }
                    }

                    if (dependentFlag) {

                        LOGGER.info("Dependent {} has been deployed.", dependentService.description());

                        break;
                    }

                    LOGGER.info("Waiting 5 seconds for deployment of dependent {}.", dependentService.description());

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 判断当前zookeeper服务是否是leader
     *
     * @return
     */
    public static boolean isLeaderConnection() {

        if (currentService == null)
            return false;

        List<ZkConnectionNodeEntity> connectionNodeEntityList = listConnection(currentService);

        if (connectionNodeEntityList == null || connectionNodeEntityList.size() == 0)
            return false;

        //排序
        Collections.sort(connectionNodeEntityList, new Comparator<ZkConnectionNodeEntity>() {
            @Override
            public int compare(ZkConnectionNodeEntity e1, ZkConnectionNodeEntity e2) {

                return e1.getId().compareTo(e2.getId());
            }
        });

        //暂定：所有zookeeper服务中第一个id为ConfigurationUtil.getServerId()的服务为leader
        return connectionNodeEntityList.get(0).getId().equals(ConfigurationUtil.getServerId());
    }

    /**
     * 服务配置读取
     *
     * @param serviceEnum 服务枚举
     * @param key         服务配置key
     * @return 服务配置实体
     */
    public static ZkConfigurationNodeEntity getServiceConfig(ServiceEnum serviceEnum, String key) {

        String path = SERVICE_CONFIG_PATH + "/" + serviceEnum.getZookeeperNodeName();

        ZkConfigurationNodeListener listener = SERVICE_CONFIG_MAP.get(path);

        return listener == null ? null : listener.getConfiguration(key);
    }

    private static void updateServiceConfig(ServiceEnum serviceEnum, ZkConfigurationNodeEntity zkConfigurationNodeEntity) {

        String path = SERVICE_CONFIG_PATH + "/" + serviceEnum.getZookeeperNodeName() + "/" + zkConfigurationNodeEntity.getKey();

        Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(path, zkConfigurationNodeEntity.getValue());

        try {
            if (checkNode(path)) {

                updateNode(entry);

            } else {
                //获取节点，没有时，抛异常
                throw new RuntimeException("There is no zookeeper node named \"" + path + "\".");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

    }

    /**
     * 服务配置修改（只能修改服务配置实体的value）
     *
     * @param configurationNodeEntity 服务配置实体
     */
    public static void updateServiceConfig(ZkConfigurationNodeEntity configurationNodeEntity) {

        updateServiceConfig(currentService, configurationNodeEntity);
    }

    /**
     * 基础服务配置读取
     *
     * @param infrastructureEnum 基础服务枚举
     * @param key                基础服务配置key
     * @return 基础服务配置实体
     */
    public static ZkConfigurationNodeEntity getInfrastructure(InfrastructureEnum infrastructureEnum, String key) {

        String path = INFRASTRUCTURE_PATH + "/" + infrastructureEnum.getZookeeperNodeName();

        ZkConfigurationNodeListener listener = INFRASTRUCTURE_MAP.get(path);

        return listener == null ? null : listener.getConfiguration(key);
    }

    /**
     * 读取指定服务下的所有配置
     *
     * @param serviceEnum 服务枚举
     * @return
     */
    public static List<ZkConfigurationNodeEntity> listServiceConfig(ServiceEnum serviceEnum) {

        String path = SERVICE_CONFIG_PATH + "/" + serviceEnum.getZookeeperNodeName();

        ZkConfigurationNodeListener listener = SERVICE_CONFIG_MAP.get(path);

        return listener == null ? null : listener.listServiceConfig();
    }

    /**
     * 修改kafka的high level消费offset
     *
     * @param groupId
     * @param topic
     * @param partition
     * @param offset
     */
    public static void updateConsumerOffset(final String groupId, final String topic, final int partition, final long offset) {

        //zookeeper节点格式：/consumers/{groupId}/offsets/{topic}/{partition}	->		offset值

        String namespace = "consumers";
        String path = new StringBuilder().append("/").append(groupId).append("/offsets/").append(topic).append("/").append(partition).toString();
        String value = String.valueOf(offset);

        try {
            CuratorFramework consumerClient = client.usingNamespace(namespace);

            if (consumerClient.checkExists().forPath(path) != null) {
                consumerClient.setData().forPath(path, value.getBytes("UTF-8"));
            } else {
                String[] strArr = path.split("/");

                StringBuilder sb = new StringBuilder();

                for (String str : strArr) {

                    if (str.length() == 0)
                        continue;

                    sb.append("/");
                    sb.append(str);

                    String p = sb.toString();

                    if (consumerClient.checkExists().forPath(p) == null) {
                        //创建永久节点
                        consumerClient.create().withMode(CreateMode.PERSISTENT).forPath(p, p.equals(path) ? value.getBytes("UTF-8") : null);
                    }
                }
            }
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);

            throw new RuntimeException(t);
        }

        LOGGER.info("Update consumer offset (groupId: {}; topic: {}; partition: {}; offset: {}).", groupId, topic, partition, offset);
    }

    /**
     * 获取kafka的high level消费offset
     * @param groupId
     * @param topic
     * @param partition
     * @return
     */
    public static Long getConsumerOffset(final String groupId, final String topic, final int partition) {

        //zookeeper节点格式：/consumers/{groupId}/offsets/{topic}/{partition}	->		offset值
        Long offset = null;

        String namespace = "consumers";
        String path = new StringBuilder().append("/").append(groupId).append("/offsets/").append(topic).append("/").append(partition).toString();

        try {
            CuratorFramework consumerClient = client.usingNamespace(namespace);

            if (consumerClient.checkExists().forPath(path) != null) {
                byte[] data = consumerClient.getData().forPath(path);

                offset = Long.valueOf(new String(data, "UTF-8"));
            }
        } catch (Throwable t) {
            LOGGER.error(t.getMessage(), t);

            throw new RuntimeException(t);
        }

        return offset;
    }
}
