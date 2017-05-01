package com.common.callback;

import com.common.entity.ZkConfigurationNodeEntity;
import enumeration.ServiceEnum;

/**
 * Created by madl on 2017/4/27.
 */
public interface IZkServiceConfigListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConfigurationNodeEntity configurationNodeEntity);
}
