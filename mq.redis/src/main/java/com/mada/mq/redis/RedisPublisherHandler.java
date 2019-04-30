package com.mada.mq.redis;

import com.mada.mq.services.IPublisherHandler;
import com.mada.mq.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;

/**
 * Created by madali on 2017/4/27.
 */
@Log4j2
public class RedisPublisherHandler implements IPublisherHandler {

    private final Jedis JEDIS;
    private final String TOPIC;

    public RedisPublisherHandler(String topic) {
        JEDIS = RedisUtil.connect();
        TOPIC = topic;
    }

    @Override
    public void publish(String message) {
        try {
            JEDIS.publish(TOPIC, message);
            log.info("Publish redis message: " + message);
        } catch (Throwable t) {
            log.error("Publish redis message error.", t);
        }
    }

    @Override
    public void close() {
        RedisUtil.disConnect(JEDIS);
        log.info("Close the redis publisher (Topic = {}).", TOPIC);
    }
}
