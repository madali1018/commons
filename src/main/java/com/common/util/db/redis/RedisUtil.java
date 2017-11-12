package com.common.util.db.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by madali on 2017/4/26.
 */
public class RedisUtil {

    private static final JedisPool JEDIS_POOL;

    private static final String CONSUMER_PROGRESS_KEY;

    static {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //The maximum number of active connections
        //that can be allocated from this pool at the same time, or negative for no limit.
        // 最大总jedis连接
        jedisPoolConfig.setMaxTotal(1000);
        //The maximum number of connections
        //that can remain idle in the pool, without extra ones being released, or negative for no limit.
        // 最大空闲jedis连接
        jedisPoolConfig.setMaxIdle(20);
        //The minimum number of connections
        //that can remain idle in the pool, without extra ones being created, or zero to create none.
        // 最小空闲jedis连接
        jedisPoolConfig.setMinIdle(1);
        //The maximum number of milliseconds
        //that the pool will wait (when there are no available connections)
        //for a connection to be returned before throwing an exception, or -1 to wait indefinitely.
        // 阻塞等待jedis可用连接的最大等待时间
        jedisPoolConfig.setMaxWaitMillis(5000L);
        //向调用者输出“链接”资源时，是否检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取
        jedisPoolConfig.setTestOnBorrow(true);
        //向连接池“归还”链接时，是否检测“链接”对象的有效性
        jedisPoolConfig.setTestOnReturn(false);
        //for JMX
        jedisPoolConfig.setJmxEnabled(true);

        //此处可以动态配置
        String redisHost = "127.0.0.1";

        String[] arr = redisHost.split(":");

        String ip = arr[0];
        int port = Integer.valueOf(arr[1]).intValue();

        JEDIS_POOL = new JedisPool(
                jedisPoolConfig,
                ip,
                port,
                4000
//                env.getProperty("redis.password"),
//                env.getProperty("redis.index", int.class, 1)
        );

        //此处可以动态配置
        CONSUMER_PROGRESS_KEY = "OffsetInfo";
    }

    public static Jedis connect() {

        return JEDIS_POOL.getResource();
    }

    public static void disconnect(Jedis jedis) {

        if (jedis != null)
            JEDIS_POOL.returnResourceObject(jedis);
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

            jedis.hset(CONSUMER_PROGRESS_KEY, groupId + "_" + topic + "_" + partition, String.valueOf(offset));

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

            if (offsetStr != null)
                offset = Long.valueOf(offsetStr);

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

                if (key.startsWith(keyPrefix))
                    partitions.add(Integer.valueOf(key.substring(keyPrefix.length())));
            }

        } finally {
            disconnect(jedis);
        }

        return partitions;
    }
}
