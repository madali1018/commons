package com.common.util.mq.kafka;

import com.common.util.mq.IPublisherHandler;

import java.util.Properties;

/**
 * Created by madl on 2017/4/27.
 */
public class KafkaPublisherHandler extends KafkaProducerHandler implements IPublisherHandler {

    public KafkaPublisherHandler(String topic) {

        super(topic);
    }

    public KafkaPublisherHandler(String topic, Properties props) {

        super(topic, props);
    }

    @Override
    public void publish(String message) {

        super.produce(message);
    }

    @Override
    public void publish(String message, String key) {

        super.produce(message, key);
    }
}
