package com.common.callback;

import com.common.entity.ZkConnectionNodeEntity;
import enumeration.ServiceEnum;

/**
 * Created by madl on 2017/4/27.
 */
public interface IZkConnectionListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);
}
