package com.mada.es.test;

import com.mada.es.impl.EsSearchServiceImpl;
import org.junit.Test;

import java.util.Map;

/**
 * Created by madali on 2019/1/16 19:15
 */
public class IEsSearchServiceTest extends BaseTest {

    String indexName = "index0116-2";
    EsSearchServiceImpl esSearchService = new EsSearchServiceImpl();

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

}
