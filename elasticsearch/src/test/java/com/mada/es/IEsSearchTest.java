package com.mada.es;

import com.mada.es.services.impl.EsSearchImpl;
import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2019/1/16 19:15
 */
public class IEsSearchTest extends BaseTest {

    String indexName = "index0116-2";
    EsSearchImpl esSearchService = new EsSearchImpl();

    @Test
    public void t1() {
        String str = esSearchService.getIndexData(indexName, "2");
        System.out.println(str);
    }

    @Test
    public void test2() {
        // 获取索引分片，副本数，创建时间等信息
        Map<String, String> settingsMap = esSearchService.getIndexSettings(indexName);
        for (Map.Entry<String, String> entry : settingsMap.entrySet()) {
            System.out.println("es集群信息:" + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("------------------");

        // 获取字段和类型
        Map<String, Object> mappingsMap = esSearchService.getIndexMappings(indexName);
        for (Map.Entry<String, Object> entry : mappingsMap.entrySet()) {
            System.out.println("es索引的字段:" + entry.getKey() + ",类型" + entry.getValue());
        }
    }

    // in not/in 一个字段
    @Test
    public void t3() {
        Set<Object> set = new HashSet<>();
        set.add("100");
        set.add("101");
        set.add("102");

        String fieldName = "price";
        List<String> result = esSearchService.getInOneFieldData(indexName, fieldName, set);
        List<String> result2 = esSearchService.getNotInOneFieldData(indexName, fieldName, set);
        result.forEach(System.out::println);
        System.out.println("====================");
        result2.forEach(System.out::println);
    }

    // in 多个字段
    @Test
    public void t4() {

        Map<String, Set<Object>> map = new HashMap<>();

        Set<Object> set = new HashSet<>();
        set.add("100");
        set.add("101");
        set.add("102");

        String fieldName = "price";
        map.put(fieldName, set);

        Set<Object> set2 = new HashSet<>();
        set2.add("1000");
        set2.add("1001");

        String fieldName2 = "time";
        map.put(fieldName2, set2);

        List<String> result = esSearchService.getInMultiFieldData(indexName, map);
        result.forEach(System.out::println);
    }

    // not in 多个字段
    @Test
    public void t5() {

        Map<String, Set<Object>> map = new HashMap<>();

        Set<Object> set = new HashSet<>();
        set.add("100");
        set.add("101");
        set.add("102");

        String fieldName = "price";
        map.put(fieldName, set);

        Set<Object> set2 = new HashSet<>();
        set2.add("1000");
        set2.add("1004");

        String fieldName2 = "time";
        map.put(fieldName2, set2);

        List<String> result = esSearchService.getNotInMultiFieldData(indexName, map);
        result.forEach(System.out::println);
    }

    //多字段排序2
    @Test
    public void test6() {

        Map<String, Boolean> map = new HashMap<>();
        map.put("price", false);
        map.put("age", true);

        List<String> result = esSearchService.getOrderData(indexName, map);
        result.forEach(System.out::println);
    }

}
