package com.mada.zookeeper.listener;

import com.mada.zookeeper.callback.IZkConnectionListenerCallback;
import com.mada.zookeeper.callback.IZkInfrastructureListenerCallback;
import com.mada.zookeeper.callback.IZkServiceConfigListenerCallback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by madali on 2017/4/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZkInitializationData {

    //服务配置监听回调
    private IZkServiceConfigListenerCallback serviceConfigListenerCallback;

    //基础服务配置监听回调
    private IZkInfrastructureListenerCallback infrastructureListenerCallback;

    //服务连接监听回调
    private IZkConnectionListenerCallback connectionListenerCallback;

}
