package com.mada.mq.utils;

import com.mada.mq.redis.RedisConsumerHandler;
import com.mada.mq.redis.RedisProducerHandler;
import com.mada.mq.redis.RedisPublisherHandler;
import com.mada.mq.redis.RedisSubscriberHandler;
import com.mada.mq.services.IConsumerHandler;
import com.mada.mq.services.IProducerHandler;
import com.mada.mq.services.IPublisherHandler;
import com.mada.mq.services.ISubscriberHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
