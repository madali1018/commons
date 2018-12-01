package com.mada.commons.util.mq.kafka;

import com.mada.commons.util.mq.ICallback;

/**
 * Created by madali on 2017/4/27.
 */
public interface IKafkaCallback extends ICallback {

    public void execute(int partition, long offset, String message);
}
