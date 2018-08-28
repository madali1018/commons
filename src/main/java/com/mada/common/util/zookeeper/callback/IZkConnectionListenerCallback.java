package com.mada.common.util.zookeeper.callback;

import com.mada.common.util.zookeeper.entity.ZkConnectionNodeEntity;
import com.mada.common.enumeration.ServiceEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkConnectionListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);
}
