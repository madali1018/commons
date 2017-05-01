package com.common.util.mq.kafka;

import com.common.util.mq.ICallback;

/**
 * Created by madl on 2017/4/27.
 */
public interface IKafkaCallback extends ICallback {

    public void execute(int partition, long offset, String message);
}
