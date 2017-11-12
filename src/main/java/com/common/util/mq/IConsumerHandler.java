package com.common.util.mq;

/**
 * Created by madali on 2017/4/27.
 */
//P2P模式
public interface IConsumerHandler {

    /**
     * 消费消息
     *
     * @param callback
     * @param <T>
     */
    <T extends ICallback> void consume(T callback);

    /**
     * 关闭消费
     */
    void shutdown();

    /**
     * 保存消费进度
     *
     * @param partition
     * @param offset
     */
    default void commit(int partition, long offset) {
    }
}
