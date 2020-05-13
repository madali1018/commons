package com.mada.mq.demo;

import com.mada.mq.services.p2p.IProducerHandler;
import com.mada.mq.services.pubsub.IPublisherHandler;
import com.mada.mq.utils.MqUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by madali on 2017/5/1.
 */
public class RedisProducerDemo {

    private static String topic = "DemoTest";

    public static void main(String[] args) throws Exception {
        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            MqUtil.disconnect();
        }));

//        testProducer();

        testPublisher();
    }

    private static void testProducer() throws Exception {
        IProducerHandler producerHandler = MqUtil.Redis.createProducerHandler(topic);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Input message:");
            producerHandler.produce(br.readLine());
        }
    }

    private static void testPublisher() throws Exception {
        IPublisherHandler publisherHandler = MqUtil.Redis.createPublisherHandler(topic);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Input message:");
            publisherHandler.publish(br.readLine());
        }
    }
}
