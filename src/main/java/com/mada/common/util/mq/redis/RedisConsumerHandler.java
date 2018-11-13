package com.mada.common.util.mq.redis;

import com.mada.common.util.redis.RedisUtil;
import com.mada.common.util.mq.ICallback;
import com.mada.common.util.mq.IConsumerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by madali on 2017/4/27.
 */
public class RedisConsumerHandler implements IConsumerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConsumerHandler.class);

    private static final int BLOCK_TIMEOUT = 30;    //30秒

    private final Jedis JEDIS;
    private final String TOPIC;

    private RedisConsumerHandler() {
        this(null);
    }

    public RedisConsumerHandler(String topic) {

        JEDIS = RedisUtil.connect();
        TOPIC = topic;
    }

    @Override
    public <T extends ICallback> void consume(final T callback) {

        try {
            while (true) {

                //producer/Consumer
                List<String> msgs = JEDIS.brpop(BLOCK_TIMEOUT, TOPIC);

                if (msgs == null)
                    continue;

                String message = msgs.get(1);

                ((IRedisCallback) callback).execute(message);
                LOGGER.info("Consume redis message: " + message);
            }
        } catch (Throwable t) {
            LOGGER.error("Consume redis message error.", t);
        }
    }

    @Override
    public void shutdown() {
        RedisUtil.disConnect(JEDIS);
        LOGGER.info("Shut down the redis consumer (Topic = {}).", TOPIC);
    }
}
