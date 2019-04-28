package com.mada.zookeeper.callback;

import com.mada.zookeeper.entity.ZkConfigurationNodeEntity;
import com.mada.zookeeper.enumeration.InfrastructureEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkInfrastructureListenerCallback {

    void onAdd(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
