package com.common.util.mq.demo;

import com.common.enumeration.ServiceEnum;
import com.common.util.mq.IConsumerHandler;
import com.common.util.mq.ISubscriberHandler;
import com.common.util.mq.MqUtil;
import com.common.util.mq.kafka.IKafkaCallback;
import com.common.util.zookeeper.ZkUtil;

/**
 * Created by madali on 2017/5/1.
 */
public class KafkaConsumerDemo {

    static {
        ServiceEnum serviceEnum = null;

        ZkUtil.connect(serviceEnum);
    }

    public static void main(String[] args) throws Exception {

        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            MqUtil.disconnect();
        }));

        testConsumer();

//        testSubscriber();
    }

    private static void testConsumer() throws Exception {

        String topic = "DemoTest";

        IConsumerHandler consumerHandler = MqUtil.Kafka.createConsumerHandler(topic);

        consumerHandler.consume((IKafkaCallback) (partition, offset, message) -> {
            System.out.println("Partition: " + partition + "\t\tOffset: " + offset + "\t\tMessage: " + message);

//            try {
//                Thread.sleep(10 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            throw new RuntimeException("test");
//            System.out.println("Sleep 10 seconds.");
//            try {
//                Thread.sleep(10*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });
    }

    private static void testSubscriber() throws Exception {

        String topic = "DemoTest";
        String groupId = "test";

        ISubscriberHandler subscriberHandler = MqUtil.Kafka.createSubscriberHandler(topic, groupId);

        subscriberHandler.subscribe((IKafkaCallback) (partition, offset, message) -> {
            System.out.println("Message:\t" + message);
            throw new RuntimeException("test");
        });
    }
}
