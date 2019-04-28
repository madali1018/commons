package com.mada.commons.util.mq.demo;

import com.mada.commons.enumeration.ServiceEnum;
import com.mada.commons.util.mq.IProducerHandler;
import com.mada.commons.util.mq.IPublisherHandler;
import com.mada.commons.util.mq.MqUtil;
import com.mada.commons.util.zookeeper.ZkUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by madali on 2017/5/1.
 */
public class RedisProducerDemo {

    static {
        ServiceEnum serviceEnum = null;

        ZkUtil.connect(serviceEnum);
    }

    public static void main(String[] args) throws Exception{

        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            MqUtil.disconnect();
        }));

//        testProducer();

        testPublisher();
    }

    private static void testProducer() throws Exception {

        String topic = "DemoTest";

        IProducerHandler producerHandler = MqUtil.Redis.createProducerHandler(topic);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input message:");
            producerHandler.produce(br.readLine());
        }
    }

    private static void testPublisher() throws Exception {

        String topic = "DemoTest";

        IPublisherHandler publisherHandler = MqUtil.Redis.createPublisherHandler(topic);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input message:");
            publisherHandler.publish(br.readLine());
        }
    }
}
