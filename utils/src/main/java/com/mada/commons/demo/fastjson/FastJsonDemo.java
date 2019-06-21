package com.mada.commons.demo.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.PropertyFilter;
import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2017/11/6.
 */
public class FastJsonDemo {

    @Test
    public void t1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "1");
        jsonObject.put("b", "2");
        System.out.println(jsonObject);

        jsonObject.remove("a");
        jsonObject.remove("a2222");
        System.out.println(jsonObject);
        System.out.println(jsonObject.containsKey("b"));
    }

    @Test
    public void t2() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "1");
        jsonObject.put("b", "2");
        System.out.println(jsonObject);

        String array = "[{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"1.125\",\"time\": 1508727704},{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"0.842\",\"time\": 1508727704}]";
        JSONArray jsonArray = JSONArray.parseArray(array);
        jsonObject.put("array", jsonArray);
        System.out.println(jsonArray);
        System.out.println(jsonObject);
    }

    // list、map 和 json 互转
    @Test
    public void t3() {
        List<Person> list = new ArrayList<>();
        Person p1 = Person.builder().id(1).address("11111").build();
        Person p2 = Person.builder().id(2).address("22222").build();
        list.add(p1);
        list.add(p2);

        String json = JSON.toJSONString(list);
        System.out.println("list转json:" + json);
        List<Person> list2 = JSON.parseObject(json, new TypeReference<List<Person>>() {
        });
        System.out.println("json转list,遍历输出每个元素:");
        list2.forEach(System.out::println);

        System.out.println("------------");

        Map<Integer, Person> map = new HashMap<>();
        map.put(1, p1);
        map.put(2, p2);
        String json2 = JSON.toJSONString(map);
        System.out.println("map转json:" + json2);
        Map<Integer, Person> map2 = JSON.parseObject(json2, new TypeReference<Map<Integer, Person>>() {
        });
        System.out.println("json转map,遍历输出每个元素:");
        map2.entrySet().stream().map(entry -> "key:" + entry.getKey() + ",value:" + entry.getValue()).forEach(System.out::println);
    }

    // Java对象 和 json 互转
    @Test
    public void test4() {
        Person p = Person.builder().id(1).age(18).name("中").address("11111").build();
        String json = JSON.toJSONString(p);
        System.out.println("java对象转json:" + json);

        String json2 = JSON.toJSONString(p, PropertyFilterImpl.INSTANCE);
        System.out.println("java对象转json(使用自定义fastjson的拦截器过滤不需要的字段):" + json2);

        //json转Java对象
        Person p2 = JSONObject.parseObject(json, Person.class);
        Person p3 = JSON.parseObject(json, Person.class);
        System.out.println("json转java对象:" + p2);
        System.out.println("json转java对象:" + p3);
    }

    private static class PropertyFilterImpl implements PropertyFilter {
        private static PropertyFilterImpl INSTANCE = new PropertyFilterImpl();

        @Override
        public boolean apply(Object object, String name, Object value) {
            //表示id和address字段将被排除在外
            return !(Objects.equals("address", name) || Objects.equals("id", name));
        }
    }

}
