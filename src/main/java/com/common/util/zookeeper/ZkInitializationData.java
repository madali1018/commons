package com.common.util.zookeeper;

import com.common.util.zookeeper.callback.IZkConnectionListenerCallback;
import com.common.util.zookeeper.callback.IZkInfrastructureListenerCallback;
import com.common.util.zookeeper.callback.IZkServiceConfigListenerCallback;

/**
 * Created by madali on 2017/4/27.
 */
public class ZkInitializationData {

    //服务配置监听回调
    private IZkServiceConfigListenerCallback serviceConfigListenerCallback;

    //基础服务配置监听回调
    private IZkInfrastructureListenerCallback infrastructureListenerCallback;

    //服务连接监听回调
    private IZkConnectionListenerCallback connectionListenerCallback;

    public IZkServiceConfigListenerCallback getServiceConfigListenerCallback() {
        return serviceConfigListenerCallback;
    }

    public void setServiceConfigListenerCallback(IZkServiceConfigListenerCallback serviceConfigListenerCallback) {
        this.serviceConfigListenerCallback = serviceConfigListenerCallback;
    }

    public IZkInfrastructureListenerCallback getInfrastructureListenerCallback() {
        return infrastructureListenerCallback;
    }

    public void setInfrastructureListenerCallback(IZkInfrastructureListenerCallback infrastructureListenerCallback) {
        this.infrastructureListenerCallback = infrastructureListenerCallback;
    }

    public IZkConnectionListenerCallback getConnectionListenerCallback() {
        return connectionListenerCallback;
    }

    public void setConnectionListenerCallback(IZkConnectionListenerCallback connectionListenerCallback) {
        this.connectionListenerCallback = connectionListenerCallback;
    }
}
