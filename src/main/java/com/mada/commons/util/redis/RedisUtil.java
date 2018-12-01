package com.mada.commons.util.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.*;

/**
 * Redis3.0之后支持地理位置geo功能。geo底层是一个zset（删除可以使用 del location命令删除）
 * 参考：https://segmentfault.com/a/1190000009857124
 * <p>
 * <p>
 * <p>
 * Created by madali on 2017/4/26.
 */
public class RedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    private static final JedisPool jedisPool;

    //kafka消费key：此处可以动态配置
    private static final String CONSUMER_PROGRESS_KEY = "OffsetInfo";

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

    public static Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (StringUtils.isNotEmpty(key)) {
                return jedis.get(key);
            }
        } catch (Exception e) {
            LOGGER.error("getObject获取redis键值异常:key=" + key + " cause:" + e.getMessage());
        } finally {
            disConnect(jedis);
        }
        return null;
    }

    public static String setObject(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, String.valueOf(value));
        } catch (Exception e) {
            LOGGER.error("setObject设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
            return null;
        } finally {
            disConnect(jedis);
        }
    }

    public static void deleteObject(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            LOGGER.error("deleteObject失败,key:{}", key);
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 增加地理位置的坐标（对应的redis命令示例：geoadd location 116.999 39.999 test1）
     *
     * @param key        redis的key：location
     * @param coordinate 经纬度对象：
     * @param memberName redis中zset对象的value：test1
     * @return
     */
    public static Long geoAdd(String key, GeoCoordinate coordinate, String memberName) {
        Jedis jedis = null;
        try {
            jedis = connect();
            return jedis.geoadd(key, coordinate.getLongitude(), coordinate.getLatitude(), memberName);
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 批量添加地理位置
     *
     * @param key                 redis的key：location
     * @param memberCoordinateMap map的key为zset对象的value：test1 test2... value为经纬度对象
     * @return
     */
    public static Long geoAdd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.geoadd(key, memberCoordinateMap);
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序，）
     * <p>
     * 对应的redis命令示例：
     * georadius location 116.999 39.999 20 km withdist
     * georadius location 116.999 39.999 20 km withdist withhash
     *
     * @param key        redis的key：location
     * @param coordinate 经纬度对象：
     * @param radius     距离：2，单位可设为km m...
     * @return List<GeoRadiusResponse>
     */
    public static List<GeoRadiusResponse> geoRadius(String key, GeoCoordinate coordinate, double radius) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.georadius(key, coordinate.getLongitude(), coordinate.getLatitude(), radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序）
     * <p>
     * 对应的redis命令示例：（和georadius一样都可以找出位于指定范围内的元素，但georadiusbymember的中心点是由给定的位置元素决定的。）
     * georadiusbymember location test1 20 km withdist
     * georadiusbymember location test1 20 km withdist withhash
     *
     * @param key    redis的key：location
     * @param member redis中zset对象的value：test1
     * @param radius 距离：2，单位可设为km m..
     * @return List<GeoRadiusResponse>
     */
    public static List<GeoRadiusResponse> geoRadiusByMember(String key, String member, double radius) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.georadiusByMember(key, member, radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 查询两位置距离（对应的redis命令示例：geodist location test1 test2 km）
     *
     * @param key     redis的key：location
     * @param member1 redis中zset对象的value：test1
     * @param member2 redis中zset对象的value：test2
     * @param unit    距离单位：km m...
     * @return
     */
    public static Double geoDist(String key, String member1, String member2, GeoUnit unit) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.geodist(key, member1, member2, unit);
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 获取某个地理位置的geohash值（对应的redis命令示例：geohash location test1）
     *
     * @param key     redis的key：location
     * @param members redis中zset对象的value：test1，test2...
     * @return
     */
    public static List<String> geoHash(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.geohash(key, members);
        } finally {
            disConnect(jedis);
        }
    }

    /**
     * 获取地理位置的坐标（对应的redis命令示例：geopos location test1）
     *
     * @param key     redis的key：location
     * @param members redis中zset对象的value：test1，test2...
     * @return
     */
    public static List<GeoCoordinate> geoPos(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.geopos(key, members);
        } finally {
            disConnect(jedis);
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
            disConnect(jedis);
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
            disConnect(jedis);
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
            disConnect(jedis);
        }

        return partitions;
    }
}
