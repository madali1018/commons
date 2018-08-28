package com.mada.common.util.mq;

import com.mada.common.util.mq.kafka.KafkaConsumerHandler;
import com.mada.common.util.mq.kafka.KafkaProducerHandler;
import com.mada.common.util.mq.kafka.KafkaPublisherHandler;
import com.mada.common.util.mq.kafka.KafkaSubscriberHandler;
import com.mada.common.util.mq.redis.RedisConsumerHandler;
import com.mada.common.util.mq.redis.RedisProducerHandler;
import com.mada.common.util.mq.redis.RedisPublisherHandler;
import com.mada.common.util.mq.redis.RedisSubscriberHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by madali on 2017/4/27.
 */
public class MqUtil {

private static final List<IProducerHandler> PRODUCER_HANDLERS = Collections.synchronizedList(new LinkedList<>());
private static final List<IConsumerHandler> CONSUMER_HANDLERS = Collections.synchronizedList(new LinkedList<>());

private static final List<IPublisherHandler> PUBLISHER_HANDLERS = Collections.synchronizedList(new LinkedList<>());
private static final List<ISubscriberHandler> SUBSCRIBER_HANDLERS = Collections.synchronizedList(new LinkedList<>());

public static void disconnect() {

        PRODUCER_HANDLERS.forEach(IProducerHandler::close);

        CONSUMER_HANDLERS.forEach(IConsumerHandler::shutdown);

        PUBLISHER_HANDLERS.forEach(IPublisherHandler::close);

        SUBSCRIBER_HANDLERS.forEach(ISubscriberHandler::shutdown);
        }

public static class Kafka {
    public static IProducerHandler createProducerHandler(String topic) {

        return createProducerHandler(topic, null);
    }

    public static IProducerHandler createProducerHandler(String topic, Properties props) {

        IProducerHandler producerHandler = props == null ? new KafkaProducerHandler(topic) : new KafkaProducerHandler(topic, props);
        PRODUCER_HANDLERS.add(producerHandler);

        return producerHandler;
    }

    public static IConsumerHandler createConsumerHandler(String topic) {

        return createConsumerHandler(topic, null);
    }

    public static IConsumerHandler createConsumerHandler(String topic, Properties props) {

        return createConsumerHandler(topic, props, true);
    }

    public static IConsumerHandler createConsumerHandler(String topic, boolean autoCommit) {

        return createConsumerHandler(topic, null, autoCommit);
    }

    public static IConsumerHandler createConsumerHandler(String topic, Properties props, boolean autoCommit) {

        String groupId = "default_kafka_consumer_group";

        IConsumerHandler consumerHandler = props == null ? new KafkaConsumerHandler(topic, groupId, autoCommit) : new KafkaConsumerHandler(topic, groupId, props, autoCommit);
        CONSUMER_HANDLERS.add(consumerHandler);

        return consumerHandler;
    }

    public static IPublisherHandler createPublisherHandler(String topic) {

        return createPublisherHandler(topic, null);
    }

    public static IPublisherHandler createPublisherHandler(String topic, Properties props) {

        IPublisherHandler publisherHandler = props == null ? new KafkaPublisherHandler(topic) : new KafkaPublisherHandler(topic, props);
        PUBLISHER_HANDLERS.add(publisherHandler);

        return publisherHandler;
    }

    public static ISubscriberHandler createSubscriberHandler(String topic, String groupId) {

        return createSubscriberHandler(topic, groupId, null);
    }

    public static ISubscriberHandler createSubscriberHandler(String topic, String groupId, Properties props) {

        return createSubscriberHandler(topic, groupId, props, true);
    }

    public static ISubscriberHandler createSubscriberHandler(String topic, String groupId, boolean autoCommit) {

        return createSubscriberHandler(topic, groupId, null, autoCommit);
    }

    public static ISubscriberHandler createSubscriberHandler(String topic, String groupId, Properties props, boolean autoCommit) {

        ISubscriberHandler subscriberHandler = props == null ? new KafkaSubscriberHandler(topic, groupId, autoCommit) : new KafkaSubscriberHandler(topic, groupId, props, autoCommit);
        SUBSCRIBER_HANDLERS.add(subscriberHandler);

        return subscriberHandler;
    }
}

public static class Redis {
    public static IProducerHandler createProducerHandler(String topic) {

        IProducerHandler producerHandler = new RedisProducerHandler(topic);
        PRODUCER_HANDLERS.add(producerHandler);

        return producerHandler;
    }

    public static IConsumerHandler createConsumerHandler(String topic) {

        IConsumerHandler consumerHandler = new RedisConsumerHandler(topic);
        CONSUMER_HANDLERS.add(consumerHandler);

        return consumerHandler;
    }

    public static IPublisherHandler createPublisherHandler(String topic) {

        IPublisherHandler publisherHandler = new RedisPublisherHandler(topic);
        PUBLISHER_HANDLERS.add(publisherHandler);

        return publisherHandler;
    }

    public static ISubscriberHandler createSubscriberHandler(String topic) {

        ISubscriberHandler subscriberHandler = new RedisSubscriberHandler(topic);
        SUBSCRIBER_HANDLERS.add(subscriberHandler);

        return subscriberHandler;
    }
}
}
