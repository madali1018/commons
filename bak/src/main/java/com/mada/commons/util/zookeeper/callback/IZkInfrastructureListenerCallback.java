package com.mada.commons.util.zookeeper.callback;

import com.mada.commons.enumeration.InfrastructureEnum;
import com.mada.commons.util.zookeeper.entity.ZkConfigurationNodeEntity;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkInfrastructureListenerCallback {

    void onAdd(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(InfrastructureEnum infrastructureEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
