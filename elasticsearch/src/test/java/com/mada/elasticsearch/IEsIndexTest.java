package com.mada.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.mada.elasticsearch.impl.EsIndexImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2019/1/16 16:44
 */
public class IEsIndexTest extends BaseTest {

    EsIndexImpl esIndexService = new EsIndexImpl();
    String indexName = "index0116-2";

    @Test
    public void testCreateIndexWithoutId() {
        esIndexService.createIndex(indexName);
    }

    @Test
    public void t1() {
        for (int i = 0; i < 5; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("price", 100 + i);
            jsonObject.put("age", 10 + i);
            if (i % 3 == 0) {
                jsonObject.put("id", 1);
            } else {
                jsonObject.put("id", 2);
            }
            jsonObject.put("content", "content " + i);
            if (i % 2 == 0) {
                jsonObject.put("title", "朝阳 " + i);
            } else {
                jsonObject.put("title", "海淀 " + i);
            }
            jsonObject.put("description", "description " + i);
            jsonObject.put("time", 1000 + i);

            System.out.println(jsonObject.toString());
            boolean result = esIndexService.createIndex(indexName, String.valueOf(i), jsonObject.toString());
            System.out.println(result);
        }
    }

    // 批量写入
    @Test
    public void t2() {

        String json1 = "{\"price\":105,\"description\":\"description5\",\"id\":5,\"time\":5,\"title\":\"朝阳5\",\"age\":15,\"content\":\"content15\"}";
        String json2 = "{\"price\":106,\"description\":\"description6\",\"id\":6,\"time\":6,\"title\":\"朝阳6\",\"age\":16,\"content\":\"content16\"}";

        Map<String, String> map = new HashMap<>();
        map.put("5", json1);
        map.put("6", json2);

        Map<String, Boolean> resultMap = esIndexService.createMultiplyDocument(indexName, map);
        for (Map.Entry<String, Boolean> entry : resultMap.entrySet()) {
            System.out.println("写入结果:id" + entry.getKey() + "," + entry.getValue());
        }

    }

    @Test
    public void t3() {
        esIndexService.deleteIndex(indexName);
    }

    @Test
    public void t4() {
        esIndexService.deleteDocument(indexName, "0");
    }

    // 批量删除
    @Test
    public void t5() {
        List<String> idList = Arrays.asList("1", "2");
        Map<String, Boolean> resultMap = esIndexService.deleteDocumentList(indexName, idList);
        for (Map.Entry<String, Boolean> entry : resultMap.entrySet()) {
            System.out.println("删除结果:id" + entry.getKey() + "," + entry.getValue());
        }
    }

    @Test
    public void t6() {
        String json = "{\"content\":\"content33333\"}";
        boolean result = esIndexService.updateIndex(indexName, "3", json);
        System.out.println(result);
    }

    @Test
    public void t7() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("content", "content3");
        fieldMap.put("description", "description3-3");
        boolean result = esIndexService.updateIndex(indexName, "3", fieldMap);
        System.out.println(result);
    }

}
