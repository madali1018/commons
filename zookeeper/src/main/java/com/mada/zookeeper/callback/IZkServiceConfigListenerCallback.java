package com.mada.zookeeper.callback;

import com.mada.zookeeper.entity.ZkConfigurationNodeEntity;
import com.mada.zookeeper.enumeration.ServiceEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkServiceConfigListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
