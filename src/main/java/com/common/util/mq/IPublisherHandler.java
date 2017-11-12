package com.common.util.mq;

/**
 * Created by madali on 2017/4/27.
 */
//Pub/Sub模式
public interface IPublisherHandler {

    /**
     * 发布消息
     *
     * @param message
     */
    void publish(String message);

    /**
     * 往指定key发布消息
     *
     * @param message
     * @param key
     */
    default void publish(String message, String key) {
        this.publish(message);
    }

    /**
     * 关闭发布
     */
    void close();
}
