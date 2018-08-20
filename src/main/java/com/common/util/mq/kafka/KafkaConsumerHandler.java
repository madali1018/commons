package com.common.util.mq.kafka;

import com.common.util.configuration.ConfigurationUtil;
import com.common.util.redis.RedisUtil;
import com.common.util.mq.ICallback;
import com.common.util.mq.IConsumerHandler;
import com.common.util.zookeeper.ZkUtil;
import com.common.enumeration.InfrastructureEnum;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by madali on 2017/4/27.
 */
public class KafkaConsumerHandler implements IConsumerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerHandler.class);

    private final ConsumerConnector CONSUMER;
    private final String TOPIC;
    private final String GROUP_ID;
    private final boolean AUTO_COMMIT;

    private KafkaConsumerHandler() {
        this(null, null);
    }

    public KafkaConsumerHandler(String topic, String groupId) {

        this(topic, groupId, null, true);
    }

    public KafkaConsumerHandler(String topic, String groupId, Properties props) {

        this(topic, groupId, props, true);
    }

    public KafkaConsumerHandler(String topic, String groupId, boolean autoCommit) {

        this(topic, groupId, null, autoCommit);
    }

    public KafkaConsumerHandler(String topic, String groupId, Properties props, boolean autoCommit) {

        CONSUMER = Consumer.createJavaConsumerConnector(this.getConsumerConfig(ConfigurationUtil.ZOOKEEPER_HOST, groupId, props));//groupId
        TOPIC = topic;
        GROUP_ID = groupId;
        AUTO_COMMIT = autoCommit;

        List<Integer> partitions = RedisUtil.getConsumerPartitions(groupId, topic);

        for (Integer partition : partitions) {
            //获取上一次消费进度
            Long initOffset = RedisUtil.getConsumerOffset(groupId, topic, partition);

            if (initOffset != null) {
                //设置初始offset
                initOffset++;
                ZkUtil.updateConsumerOffset(groupId, topic, partition, initOffset);
            }
        }
    }

    @Override
    public final <T extends ICallback> void consume(final T callback) {

        // key为topic名称，value为topic对应的分区数。
        // 如果kafka中不存在对应的topic,则会创建一个topic，分区数为value，
        // 如果value大于broker配置文件中num.partitions所配置的大小，则以num.partitions为准
        // 如果存在，则该处的value不起作用
        Map<String, Integer> topicCountMap = new HashMap<>();
//            topicCountMap.put(TOPIC, new Integer(threadCount));
        //一次从topic中获取一个数据
        topicCountMap.put(TOPIC, 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = CONSUMER.createMessageStreams(topicCountMap);

        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(TOPIC);

        //consume the messages
        for (final KafkaStream stream : streams) {

            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {

                MessageAndMetadata<byte[], byte[]> m = it.next();
                int partition = m.partition();
                long offset = m.offset();
                String message = null;
                try {
                    message = new String(m.message(), "UTF-8");

                    if (!"".equals(message)) //消息为空字符串时，忽略
                        ((IKafkaCallback) callback).execute(partition, offset, message);

                    if (AUTO_COMMIT)
                        this.commit(partition, offset);

                    LOGGER.info("Consume kafka message (topic: {}; groupId: {}; partition: {}; offset: {}).", TOPIC, GROUP_ID, partition, offset);
                } catch (Throwable t) {
                    if (message == null)
                        LOGGER.error("Consume kafka message error (topic: " + TOPIC + "; groupId: " + GROUP_ID + "; partition: " + partition + "; offset: " + offset + ").", t);
                    else
                        LOGGER.error("Consume kafka message error (topic: " + TOPIC + "; groupId: " + GROUP_ID + "; partition: " + partition + "; offset: " + offset + "; message: " + message + ").", t);

                    //向外抛出异常后，后续的offset不会再消费
//                        throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    @Override
    public void shutdown() {
        if (CONSUMER != null)
            CONSUMER.shutdown();

        LOGGER.info("Shut down the kafka consumer (Topic = {}).", TOPIC);
    }

    @Override
    public final void commit(int partition, long offset) {

        //保存消费进度
        RedisUtil.updateConsumerOffset(GROUP_ID, TOPIC, partition, offset);
    }

    private ConsumerConfig getConsumerConfig(String zookeeperHost, String groupId, Properties props) {

        if (props == null)
            props = new Properties();
        props.put("zookeeper.connect", zookeeperHost);
        props.put("group.id", groupId);
        //信息消费最大字节数
        props.put("fetch.message.max.bytes", ZkUtil.getInfrastructure(InfrastructureEnum.Kafka, "maxFetchMsgByte").getValue());
//        props.put("zookeeper.session.timeout.ms", "400");
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }
}
