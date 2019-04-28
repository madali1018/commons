package com.mada.commons.util.mq;

/**
 * Created by madali on 2017/4/27.
 */
//Pub/Sub模式
public interface ISubscriberHandler {

    /**
     * 订阅消息
     *
     * @param callback
     * @param <T>
     */
    <T extends ICallback> void subscribe(T callback);

    /**
     * 关闭订阅
     */
    void shutdown();

    /**
     * 保存订阅进度
     *
     * @param offset
     */
    default void commit(int partition, long offset) {
    }
}
