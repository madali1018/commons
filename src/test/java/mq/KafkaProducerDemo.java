package mq;

import enumeration.ServiceEnum;
import com.common.util.mq.IProducerHandler;
import com.common.util.mq.IPublisherHandler;
import com.common.util.mq.MqUtil;
import com.common.util.zookeeper.ZkUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by madl on 2017/5/1.
 */
public class KafkaProducerDemo {

    static {
        ServiceEnum serviceEnum = null;

        ZkUtil.connect(serviceEnum);
    }

    public static void main(String[] args) throws Exception {

        //应用关闭时，执行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            MqUtil.disconnect();
        }));

        testProducer();

//        testPublisher();
    }

    private static void testProducer() throws Exception {

        String topic = "DemoTest";

        IProducerHandler producerHandler = MqUtil.Kafka.createProducerHandler(topic);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input message:");
            producerHandler.produce(br.readLine());

//            String message = Math.random()+"";
//            producerHandler.produce(message);
//            System.out.println(message);
            Thread.sleep(1);
        }
    }

    private static void testPublisher() throws Exception {

        String topic = "DemoTest";

        IPublisherHandler publisherHandler = MqUtil.Kafka.createPublisherHandler(topic);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Input message:");
            publisherHandler.publish(br.readLine());
        }
    }
}
