package com.mada.mq.demo;

import com.mada.mq.services.p2p.IConsumerHandler;
import com.mada.mq.services.pubsub.ISubscriberHandler;
import com.mada.mq.utils.MqUtil;
import com.mada.mq.redis.IRedisCallback;

/**
 * Created by madali on 2017/5/1.
 */
public class RedisConsumerDemo {

    private static String topic = "DemoTest";

    public static void main(String[] args) {
        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            MqUtil.disconnect();
        }));

//        testConsumer();

        testSubscriber();
    }

    private static void testConsumer() {
        IConsumerHandler consumerHandler = MqUtil.Redis.createConsumerHandler(topic);
        consumerHandler.consume((IRedisCallback) message -> {
            System.out.println("Message:\t" + message);
        });
    }

    private static void testSubscriber() {
        ISubscriberHandler subscriberHandler = MqUtil.Redis.createSubscriberHandler(topic);
        subscriberHandler.subscribe((IRedisCallback) message -> {
            System.out.println("Message:\t" + message);
        });
    }
}
