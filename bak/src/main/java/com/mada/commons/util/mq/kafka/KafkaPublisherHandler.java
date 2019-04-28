package com.mada.commons.util.mq.kafka;

import com.mada.commons.util.mq.IPublisherHandler;

import java.util.Properties;

/**
 * Created by madali on 2017/4/27.
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
