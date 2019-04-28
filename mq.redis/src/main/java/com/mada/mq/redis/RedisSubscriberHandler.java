package com.mada.mq.redis;

import com.mada.mq.services.ICallback;
import com.mada.mq.services.ISubscriberHandler;
import com.mada.mq.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by madali on 2017/4/27.
 */
@Slf4j
public class RedisSubscriberHandler implements ISubscriberHandler {

    private final Jedis JEDIS;
    private final String TOPIC;

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
                    log.info("Subscribe redis message: " + message);
                }
            }, TOPIC);
        } catch (Throwable t) {
            log.error("Subscribe redis message error.", t);
        }
    }

    @Override
    public void shutdown() {
        RedisUtil.disConnect(JEDIS);
        log.info("Shut down the redis subscriber (Topic = {}).", TOPIC);
    }
}
