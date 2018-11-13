package com.mada.common.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by madali on 2017/4/26.
 */
public class RedisUtil {

    private static final JedisPool JEDIS_POOL;

    private static final String CONSUMER_PROGRESS_KEY;

    // Redis 服务器ip（如果是集群，此处需配置多个） port 密码
    private static final String IP = "127.0.0.1";
    private static final int PORT = 6379;
    private static final String PASSWORD = "";
    private static final int TIMEOUT = 4000;// 单位：毫秒

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大总jedis连接
        jedisPoolConfig.setMaxTotal(1000);
        // 最大空闲jedis连接
        jedisPoolConfig.setMaxIdle(20);
        // 最小空闲jedis连接
        jedisPoolConfig.setMinIdle(1);
        //  表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException。此处设为5000ms，设为-1表示永不超时。
        jedisPoolConfig.setMaxWaitMillis(5000L);
        //向调用者输出“链接”资源时，是否检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取，如果为true，则得到的jedis实例均是可用的；
        jedisPoolConfig.setTestOnBorrow(true);
        //向连接池“归还”链接时，是否检测“链接”对象的有效性
        jedisPoolConfig.setTestOnReturn(false);
        //是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        JEDIS_POOL = new JedisPool(jedisPoolConfig, IP, PORT, TIMEOUT, PASSWORD);

        //此处可以动态配置
        CONSUMER_PROGRESS_KEY = "OffsetInfo";
    }

    public static Jedis connect() {
        return JEDIS_POOL.getResource();
    }

    public static void disconnect(Jedis jedis) {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }

    /**
     * 更新kafka消费进度（offset）
     *
     * @param groupId
     * @param topic
     * @param partition
     * @param offset
     */
    public static void updateConsumerOffset(final String groupId, final String topic, final int partition, final long offset) {
        Jedis jedis = null;
        try {
            jedis = connect();
            jedis.hset(CONSUMER_PROGRESS_KEY, groupId + "_" + topic + "_" + partition, Long.toString(offset));
        } finally {
            disconnect(jedis);
        }
    }

    /**
     * 获取kafka消费进度（offset）
     *
     * @param groupId
     * @param topic
     * @param partition
     */
    public static Long getConsumerOffset(final String groupId, final String topic, final int partition) {
        Long offset = null;
        Jedis jedis = null;

        try {
            jedis = connect();
            String offsetStr = jedis.hget(CONSUMER_PROGRESS_KEY, groupId + "_" + topic + "_" + partition);
            if (Objects.nonNull(offsetStr)) {
                offset = Long.parseLong(offsetStr);
            }
        } finally {
            disconnect(jedis);
        }

        return offset;
    }

    /**
     * 获取kafka的指定组和topic的所有区
     *
     * @param groupId
     * @param topic
     * @return
     */
    public static List<Integer> getConsumerPartitions(final String groupId, final String topic) {
        List<Integer> partitions = new ArrayList<>();

        String keyPrefix = groupId + "_" + topic + "_";
        Jedis jedis = null;

        try {
            jedis = connect();
            Iterator<String> keyIt = jedis.hkeys(CONSUMER_PROGRESS_KEY).iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                if (key.startsWith(keyPrefix)) {
                    partitions.add(Integer.parseInt(key.substring(keyPrefix.length())));
                }
            }
        } finally {
            disconnect(jedis);
        }

        return partitions;
    }
}
