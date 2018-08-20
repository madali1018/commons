package com.common.util.zookeeper.callback;

import com.common.util.zookeeper.entity.ZkConfigurationNodeEntity;
import com.common.enumeration.InfrastructureEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkInfrastructureListenerCallback {

    void onAdd(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
