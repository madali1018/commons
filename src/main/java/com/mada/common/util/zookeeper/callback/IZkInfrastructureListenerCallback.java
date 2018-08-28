package com.mada.common.util.zookeeper.callback;

import com.mada.common.util.zookeeper.entity.ZkConfigurationNodeEntity;
import com.mada.common.enumeration.InfrastructureEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkInfrastructureListenerCallback {

    void onAdd(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
