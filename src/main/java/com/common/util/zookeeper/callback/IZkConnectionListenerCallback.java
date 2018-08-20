package com.common.util.zookeeper.callback;

import com.common.util.zookeeper.entity.ZkConnectionNodeEntity;
import com.common.enumeration.ServiceEnum;

/**
 * Created by madali on 2017/4/27.
 */
public interface IZkConnectionListenerCallback {

    void onAdd(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onUpdate(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);

    void onRemove(ServiceEnum serviceEnum, ZkConnectionNodeEntity connectionNodeEntity);
}
