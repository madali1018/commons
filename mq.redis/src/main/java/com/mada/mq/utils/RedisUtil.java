package com.mada.mq.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * Created by madali on 2017/4/26.
 */
public class RedisUtil {

    private static final JedisPool jedisPool;

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

        jedisPool = new JedisPool(jedisPoolConfig, IP, PORT, TIMEOUT, PASSWORD);
    }

    public static Jedis connect() {
        return jedisPool.getResource();
    }

    public static void disConnect(Jedis jedis) {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }

}
