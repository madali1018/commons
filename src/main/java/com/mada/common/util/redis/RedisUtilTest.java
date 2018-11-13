package com.mada.common.util.redis;

import org.junit.Test;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2018/11/13 11:12
 */
public class RedisUtilTest {

    String key = "location";

    @Test
    public void t1() {
        long t1 = System.currentTimeMillis();
        GeoCoordinate coordinate = new GeoCoordinate(116.555, 39.555);
        String memberName = "test5";
        Long result = RedisUtil.geoAdd(key, coordinate, memberName);
        System.out.println(result);
        System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void t2() {
        long t1 = System.currentTimeMillis();

        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("test6", new GeoCoordinate(116.666, 39.888));
        memberCoordinateMap.put("test7", new GeoCoordinate(116.777, 39.888));
        memberCoordinateMap.put("test8", new GeoCoordinate(116.888, 39.888));

        Long result = RedisUtil.geoAdd(key, memberCoordinateMap);
        System.out.println(result);
        System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void t3() {
        for (int i = 0; i < 100; i++) {
            long t1 = System.currentTimeMillis();
            List<GeoRadiusResponse> list = RedisUtil.geoRadiusByMember(key, "test1", 20);
            for (GeoRadiusResponse geoRadiusResponse : list) {
                System.out.println(geoRadiusResponse.getDistance() + "," + geoRadiusResponse.getMember() + "," + geoRadiusResponse.getCoordinate());
            }
            System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
        }
    }

    @Test
    public void t4() {
        long t1 = System.currentTimeMillis();

        GeoCoordinate coordinate = new GeoCoordinate(116.111, 39.111);
        double radius = 2;

        List<GeoRadiusResponse> list = RedisUtil.geoRadius(key, coordinate, radius);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void t5() {
        long t1 = System.currentTimeMillis();
        Double distance = RedisUtil.geoDist(key, "test1", "test2", GeoUnit.KM);
        System.out.println(distance);
        System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void t6() {
        long t1 = System.currentTimeMillis();
        List<String> list = RedisUtil.geoHash(key, "test1", "test2");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
    }

    @Test
    public void t7() {
        long t1 = System.currentTimeMillis();
        List<GeoCoordinate> list = RedisUtil.geoPos(key, "test1", "test2", "test10");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - t1) + "ms");
    }

}
