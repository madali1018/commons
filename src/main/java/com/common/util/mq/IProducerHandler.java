package com.common.util.mq;

/**
 * Created by madali on 2017/4/27.
 */
//P2P模式
public interface IProducerHandler {

    /**
     * 生产消息
     *
     * @param message
     */
    void produce(String message);

    /**
     * 往指定key生产消息
     *
     * @param message
     * @param key
     */
    default void produce(String message, String key) {
        this.produce(message);
    }

    /**
     * 关闭生产
     */
    void close();
}
