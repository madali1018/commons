package com.mada.mq.redis;

import com.mada.mq.services.ICallback;
import com.mada.mq.services.IConsumerHandler;
import com.mada.mq.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by madali on 2017/4/27.
 */
@Log4j2
public class RedisConsumerHandler implements IConsumerHandler {

    private static final int BLOCK_TIMEOUT = 30;    //30秒

    private final Jedis JEDIS;
    private final String TOPIC;

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

                if (msgs == null) {
                    continue;
                }

                String message = msgs.get(1);
                ((IRedisCallback) callback).execute(message);
                log.info("Consume redis message: " + message);
            }
        } catch (Throwable t) {
            log.error("Consume redis message error.", t);
        }
    }

    @Override
    public void shutdown() {
        RedisUtil.disConnect(JEDIS);
        log.info("Shut down the redis consumer (Topic = {}).", TOPIC);
    }
}
