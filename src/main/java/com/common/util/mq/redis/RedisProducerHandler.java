package com.common.util.mq.redis;

import com.common.util.db.redis.RedisUtil;
import com.common.util.mq.IProducerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by madl on 2017/4/27.
 */
public class RedisProducerHandler implements IProducerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisProducerHandler.class);

    private final Jedis JEDIS;
    private final String TOPIC;

    private RedisProducerHandler() {
        this(null);
    }

    public RedisProducerHandler(String topic) {

        JEDIS = RedisUtil.connect();
        TOPIC = topic;
    }

    @Override
    public void produce(String message) {

        try {
            JEDIS.lpush(TOPIC, message);
            LOGGER.info("Produce redis message: " + message);
        } catch (Throwable t) {
            LOGGER.error("Produce redis message error.", t);
        }
    }

    @Override
    public void close() {

        RedisUtil.disconnect(JEDIS);

        LOGGER.info("Close the redis producer (Topic = {}).", TOPIC);
    }
}
