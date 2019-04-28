package com.mada.zookeeper.callback;

import com.mada.zookeeper.entity.ZkConnectionNodeEntity;
import com.mada.zookeeper.enumeration.ServiceEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkConnectionListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);
}
