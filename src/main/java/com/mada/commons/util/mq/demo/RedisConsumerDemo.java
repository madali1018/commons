package com.mada.commons.util.mq.demo;

import com.mada.commons.enumeration.ServiceEnum;
import com.mada.commons.util.mq.IConsumerHandler;
import com.mada.commons.util.mq.ISubscriberHandler;
import com.mada.commons.util.mq.MqUtil;
import com.mada.commons.util.mq.redis.IRedisCallback;
import com.mada.commons.util.zookeeper.ZkUtil;

/**
 * Created by madali on 2017/5/1.
 */
public class RedisConsumerDemo {

    static {
        ServiceEnum serviceEnum = null;

        ZkUtil.connect(serviceEnum);
    }

    public static void main(String[] args) throws Exception {

        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            MqUtil.disconnect();
        }));

//        testConsumer();

        testSubscriber();
    }

    private static void testConsumer() throws Exception {

        String topic = "DemoTest";

        IConsumerHandler consumerHandler = MqUtil.Redis.createConsumerHandler(topic);

        consumerHandler.consume((IRedisCallback) message -> {
            System.out.println("Message:\t" + message);
        });
    }

    private static void testSubscriber() throws Exception {

        String topic = "DemoTest";

        ISubscriberHandler subscriberHandler = MqUtil.Redis.createSubscriberHandler(topic);

        subscriberHandler.subscribe((IRedisCallback) message -> {
            System.out.println("Message:\t" + message);
        });
    }
}
