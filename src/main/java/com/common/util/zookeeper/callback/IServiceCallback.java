package com.common.util.zookeeper.callback;

import com.common.util.zookeeper.callback.service.IServiceEntity;

/**
 * Created by madali on 2017/4/27.
 */
public interface IServiceCallback<T extends IServiceEntity> {

    /**
     * 业务方法的异步回调接口
     *
     * @param serviceEntity
     */
    public void completed(T serviceEntity);
}
