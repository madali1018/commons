package com.common.util.zookeeper;

import com.common.callback.IZkInfrastructureListenerCallback;
import com.common.callback.IZkServiceConfigListenerCallback;
import com.common.entity.ZkConfigurationNodeEntity;
import enumeration.InfrastructureEnum;
import enumeration.ServiceEnum;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by madl on 2017/4/27.
 */
final class ZkConfigurationNodeListener extends ZkNodeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkConfigurationNodeListener.class);

    private final Map<String, ZkConfigurationNodeEntity> configurationMap = new ConcurrentHashMap<>();

    private IZkServiceConfigListenerCallback serviceConfigListenerCallback;
    private IZkInfrastructureListenerCallback infrastructureListenerCallback;

    private ServiceEnum serviceEnum;
    private InfrastructureEnum infrastructureEnum;

    protected ZkConfigurationNodeListener(String path, IZkServiceConfigListenerCallback serviceConfigListenerCallback) {

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
        this.serviceConfigListenerCallback = serviceConfigListenerCallback;
    }

    protected ZkConfigurationNodeListener(String path, IZkInfrastructureListenerCallback infrastructureListenerCallback) {

        super(path);

        InfrastructureEnum infrastructureEnum = null;

        InfrastructureEnum[] infrastructureEnums = InfrastructureEnum.values();

        for (InfrastructureEnum ie : infrastructureEnums) {

            if (super.getNodeName().equals(ie.getZookeeperNodeName())) {
                infrastructureEnum = ie;

                break;
            }
        }

        this.infrastructureEnum = infrastructureEnum;
        this.infrastructureListenerCallback = infrastructureListenerCallback;
    }

    protected ZkConfigurationNodeListener(String path) {
        super(path);
    }

    /**
     * 添加子节点
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     */
    @Override
    public void onChildAdd(String nodeName, String nodeValue) {

        ZkConfigurationNodeEntity configurationNodeEntity = new ZkConfigurationNodeEntity(nodeName, nodeValue);

        this.configurationMap.put(nodeName, configurationNodeEntity);

        LOGGER.debug("add configuration: " + super.getNodeName() + " -> " + nodeName);

        if (this.serviceConfigListenerCallback != null)
            this.serviceConfigListenerCallback.onAdd(this.serviceEnum, configurationNodeEntity);

        if (this.infrastructureListenerCallback != null)
            this.infrastructureListenerCallback.onAdd(this.infrastructureEnum, configurationNodeEntity);
    }

    /**
     * 修改子节点
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     */
    @Override
    public void onChildUpdate(String nodeName, String nodeValue) {

        ZkConfigurationNodeEntity configurationNodeEntity = new ZkConfigurationNodeEntity(nodeName, nodeValue);

        this.configurationMap.put(nodeName, configurationNodeEntity);

        LOGGER.debug("update configuration: " + super.getNodeName() + " -> " + nodeName);

        if (this.serviceConfigListenerCallback != null)
            this.serviceConfigListenerCallback.onUpdate(this.serviceEnum, configurationNodeEntity);

        if (this.infrastructureListenerCallback != null)
            this.infrastructureListenerCallback.onUpdate(this.infrastructureEnum, configurationNodeEntity);
    }

    /**
     * 删除子节点
     *
     * @param nodeName  节点名称
     * @param nodeValue 节点值
     */
    @Override
    public void onChildRemove(String nodeName, String nodeValue) {

        ZkConfigurationNodeEntity configurationNodeEntity = new ZkConfigurationNodeEntity(nodeName, nodeValue);

        this.configurationMap.remove(nodeName);

        LOGGER.debug("remove configuration: " + super.getNodeName() + " -> " + nodeName);

        if (this.serviceConfigListenerCallback != null)
            this.serviceConfigListenerCallback.onRemove(this.serviceEnum, configurationNodeEntity);

        if (this.infrastructureListenerCallback != null)
            this.infrastructureListenerCallback.onRemove(this.infrastructureEnum, configurationNodeEntity);
    }

    /**
     * 服务配置读取
     *
     * @param key 服务配置的key
     * @return
     */
    public ZkConfigurationNodeEntity getConfiguration(String key) {

        ZkConfigurationNodeEntity configurationNodeEntity = this.configurationMap.get(key);

        if (configurationNodeEntity == null) {
            //缓存中不存在时，从zookeeper中读取
            try {
                Map.Entry<String, String> entry = ZkUtil.getNode(super.getPath() + "/" + key);

                this.onChildAdd(entry.getKey().substring(super.getPath().length() + 1), entry.getValue());

                configurationNodeEntity = this.getConfiguration(key);
            } catch (Throwable t) {

                if (t instanceof RuntimeException && t.getCause() instanceof KeeperException.NoNodeException)
                    LOGGER.warn("No configuration in {} with key {}.", super.getPath(), key);
                else
                    LOGGER.warn(t.getMessage());
            }
        }

        return configurationNodeEntity;
    }

    /**
     * 读取指定服务下的所有配置的基础方法
     *
     * @return
     */
    public List<ZkConfigurationNodeEntity> listServiceConfig() {

        List<ZkConfigurationNodeEntity> zkConfigurationNodeEntityList = new ArrayList<>(this.configurationMap.values());

        if (zkConfigurationNodeEntityList == null || zkConfigurationNodeEntityList.size() == 0)
            LOGGER.warn("No serviceConfig in {}.", super.getPath());

        return zkConfigurationNodeEntityList;
    }
}
