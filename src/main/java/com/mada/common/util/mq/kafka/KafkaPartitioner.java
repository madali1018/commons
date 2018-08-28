package com.mada.common.util.mq.kafka;

import kafka.producer.DefaultPartitioner;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2017/4/27.
 */
public class KafkaPartitioner extends DefaultPartitioner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPartitioner.class);

    public KafkaPartitioner(VerifiableProperties props) {

        super(props);
    }

    @Override
    public int partition(Object key, int numPartitions) {

        int partition = super.partition(key, numPartitions);

        LOGGER.info("Key: {}; NumPartitions: {}; Partition: {}.", key, numPartitions, partition);

        return partition;
    }
}
