package com.mada.commons.util.mq.redis;

import com.mada.commons.util.redis.RedisUtil;
import com.mada.commons.util.mq.ICallback;
import com.mada.commons.util.mq.ISubscriberHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by madali on 2017/4/27.
 */
public class RedisSubscriberHandler implements ISubscriberHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSubscriberHandler.class);

    private final Jedis JEDIS;
    private final String TOPIC;

    private RedisSubscriberHandler() {
        this(null);
    }

    public RedisSubscriberHandler(String topic) {
        JEDIS = RedisUtil.connect();
        TOPIC = topic;
    }

    @Override
    public <T extends ICallback> void subscribe(final T callback) {
        try {
            JEDIS.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    ((IRedisCallback) callback).execute(message);
                    LOGGER.info("Subscribe redis message: " + message);
                }
            }, TOPIC);
        } catch (Throwable t) {
            LOGGER.error("Subscribe redis message error.", t);
        }
    }

    @Override
    public void shutdown() {
        RedisUtil.disConnect(JEDIS);
        LOGGER.info("Shut down the redis subscriber (Topic = {}).", TOPIC);
    }
}
