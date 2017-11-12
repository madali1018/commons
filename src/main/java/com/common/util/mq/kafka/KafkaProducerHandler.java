package com.common.util.mq.kafka;

import enumeration.InfrastructureEnum;
import com.common.util.mq.IProducerHandler;
import com.common.util.zookeeper.ZkUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by madali on 2017/4/27.
 */
public class KafkaProducerHandler implements IProducerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerHandler.class);

    private final Producer<String, String> PRODUCER;
    private final String TOPIC;
    private final String DEFAULT_KEY;

    private KafkaProducerHandler() {
        this(null);
    }

    public KafkaProducerHandler(String topic) {

        this(topic, null);
    }

    public KafkaProducerHandler(String topic, Properties props) {

        String kafkaHost = ZkUtil.getInfrastructure(InfrastructureEnum.Kafka, "url").getValue();

        PRODUCER = new Producer<>(this.getProducerConfig(kafkaHost, props));
        TOPIC = topic;
        DEFAULT_KEY = topic;
    }

    @Override
    public void produce(String message) {

        this.produce(message, null);
    }

    @Override
    public void produce(String message, String key) {

        if (key == null)
            key = DEFAULT_KEY;

        try {
            KeyedMessage<String, String> keyedMessage = new KeyedMessage<>(TOPIC, key, message);

            PRODUCER.send(keyedMessage);

            LOGGER.info("Produce kafka message (topic: {}).", TOPIC);
        } catch (Throwable t) {
            LOGGER.error("Produce kafka message error (topic: " + TOPIC + "; message: " + message + ").", t);
            throw t;
        }
    }

    @Override
    public void close() {
        if (PRODUCER != null)
            PRODUCER.close();

        LOGGER.info("Close the kafka producer (Topic = {}).", TOPIC);
    }

    private ProducerConfig getProducerConfig(String zookeeperHost, Properties props) {

        if (props == null)
            props = new Properties();
        props.put("metadata.broker.list", zookeeperHost);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //指定partitioner来自定义分发机制
//        properties.put("partitioner.class", "com.common.util.mq.kafka.KafkaPartitioner");

        return new ProducerConfig(props);
    }
}
