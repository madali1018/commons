package com.mada.common.util.zookeeper.callback;

import com.mada.common.util.zookeeper.entity.ZkConfigurationNodeEntity;
import com.mada.common.enumeration.ServiceEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkServiceConfigListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
