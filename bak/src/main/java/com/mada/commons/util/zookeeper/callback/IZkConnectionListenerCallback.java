package com.mada.commons.util.zookeeper.callback;

import com.mada.commons.enumeration.ServiceEnum;
import com.mada.commons.util.zookeeper.entity.ZkConnectionNodeEntity;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkConnectionListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);
}
