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
        System.out.println(jsonObject.toString());
    }

    // JSONObject JSONArray的基础使用
    @Test
    public void test1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "1");
        jsonObject.put("b", "2");
        System.out.println(jsonObject);
        System.out.println("----------------");

        JSONArray jsonArray;
        String array = "[{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"1.125\",\"time\": 1508727704},{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"0.842\",\"time\": 1508727704}]";
        jsonArray = JSONArray.parseArray(array);
        jsonObject.put("array", jsonArray);
        System.out.println(jsonArray);
        System.out.println("----------------");

        String fieldsStr = "{\"groupid\":\"4.1.1.2.1\",\"sensorid\":2,\"time\":15215484298,\"type\":\"NH3\",\"value\":\"28.50\"}";
        JSONObject fields = JSONObject.parseObject(fieldsStr);
        System.out.println(fields);
        System.out.println(fields.containsKey("time"));
    }

    // list map转 json
    @Test
    public void test2() {
        List<Person> list = new ArrayList<>();
        Person p1 = new Person(1, "11");
        Person p2 = new Person(2, "12");
        list.add(p1);
        list.add(p2);

        String json = JSON.toJSONString(list);
        System.out.println(json);
        System.out.println("--------------------------");

        Map<Integer, Person> map = new HashMap<>();
        map.put(1, p1);
        map.put(2, p2);
        String json2 = JSON.toJSONString(map);
        System.out.println(json2);
    }

    // json 转 list map
    @Test
    public void test3() {
        String json = "[{\"age\":1,\"name\":\"11\"},{\"age\":2,\"name\":\"12\"}]";
        List<Person> list = JSON.parseObject(json, new TypeReference<List<Person>>() {
        });
        for (Person person : list) {
            System.out.println(person);
        }
        System.out.println("----------------------");

        String json2 = "{1:{\"age\":1,\"name\":\"11\"},2:{\"age\":2,\"name\":\"12\"}}";
        Map<Integer, Person> map = JSON.parseObject(json2, new TypeReference<Map<Integer, Person>>() {
        });
        for (Map.Entry<Integer, Person> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    // Java对象转json
    @Test
    public void test4() {
        Person p = new Person(1, "11");
        String json = JSON.toJSONString(p);
        System.out.println(json);
        System.out.println("-------------------------");

        //json转Java对象
        Person p2 = JSONObject.parseObject(json, Person.class);
        System.out.println(p2);
        System.out.println("-------------------------");

        //json转Java对象
        Person p3 = JSON.parseObject(json, Person.class);
        System.out.println(p3);
    }

    // 对象转json：过滤不需要转换的字段
    @Test
    public void test5() {
        Person p1 = new Person(1, 16, "11", "北京");
        String json = JSON.toJSONString(p1);
        System.out.println(json);
        // 过滤不需要的字段：自定义fastjson的拦截器
        String json2 = JSON.toJSONString(p1, PropertyFilterImpl.INSTANCE);
        System.out.println(json2);
    }

    private static class PropertyFilterImpl implements PropertyFilter {
        private static PropertyFilterImpl INSTANCE = new PropertyFilterImpl();

        @Override
        public boolean apply(Object object, String name, Object value) {
            //表示id和address字段将被排除在外
            return !Objects.equals("address", name) && !Objects.equals("id", name);
        }
    }

}
