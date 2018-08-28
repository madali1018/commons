package com.mada.common.util.mq.redis;

import com.mada.common.util.redis.RedisUtil;
import com.mada.common.util.mq.IPublisherHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by madali on 2017/4/27.
 */
public class RedisPublisherHandler implements IPublisherHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPublisherHandler.class);

    private final Jedis JEDIS;
    private final String TOPIC;

    private RedisPublisherHandler() {
        this(null);
    }

    public RedisPublisherHandler(String topic) {

        JEDIS = RedisUtil.connect();
        TOPIC = topic;
    }

    @Override
    public void publish(String message) {

        try {
            JEDIS.publish(TOPIC, message);
            LOGGER.info("Publish redis message: " + message);
        } catch (Throwable t) {
            LOGGER.error("Publish redis message error.", t);
        }
    }

    @Override
    public void close() {

        RedisUtil.disconnect(JEDIS);

        LOGGER.info("Close the redis publisher (Topic = {}).", TOPIC);
    }
}
