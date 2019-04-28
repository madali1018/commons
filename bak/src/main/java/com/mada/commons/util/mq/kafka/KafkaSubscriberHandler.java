package com.mada.commons.util.mq.kafka;

import com.mada.commons.util.mq.ICallback;
import com.mada.commons.util.mq.ISubscriberHandler;

import java.util.Properties;

/**
 * Created by madali on 2017/4/27.
 */
public class KafkaSubscriberHandler extends KafkaConsumerHandler implements ISubscriberHandler {

    public KafkaSubscriberHandler(String topic, String groupId) {

        super(topic, groupId);
    }

    public KafkaSubscriberHandler(String topic, String groupId, Properties props) {

        super(topic, groupId, props);
    }

    public KafkaSubscriberHandler(String topic, String groupId, boolean autoCommit) {

        super(topic, groupId, autoCommit);
    }

    public KafkaSubscriberHandler(String topic, String groupId, Properties props, boolean autoCommit) {

        super(topic, groupId, props, autoCommit);
    }

    @Override
    public void subscribe(ICallback callback) {

        super.consume(callback);
    }
}
