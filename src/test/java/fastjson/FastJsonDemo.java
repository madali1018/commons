package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2017/11/6.
 */
public class FastJsonDemo {

    @Test
    public void test1() {
        // fastjson中JSONObject JSONArray的基础使用
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "1");
        jsonObject.put("b", "2");

        JSONArray jsonArray;
        String array = "[{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"1.125\",\"time\": 1508727704},{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"0.842\",\"time\": 1508727704}]";
        jsonArray = JSONArray.parseArray(array);

        jsonObject.put("array", jsonArray);
        System.out.println(jsonArray);
        System.out.println("----------------");
        System.out.println(jsonObject);
        System.out.println("----------------");

        String fieldsStr = "{\"groupid\":\"4.1.1.2.1\",\"sensorid\":2,\"time\":15215484298,\"type\":\"NH3\",\"value\":\"28.50\"}";
        JSONObject fields = JSONObject.parseObject(fieldsStr);
        System.out.println(fields);
        System.out.println(fields.containsKey("time"));
    }

    @Test
    public void test2() {
        List<Person> list = new ArrayList<>();
        Person p1 = new Person(1, "11");
        Person p2 = new Person(2, "12");
        list.add(p1);
        list.add(p2);

        // list转String类型的JSON串
        String json = JSON.toJSONString(list);
        System.out.println(json);
        System.out.println("--------------------------");
        Map<Integer, Person> map = new HashMap<>();
        map.put(1, p1);
        map.put(2, p2);

        // map转String类型的JSON串 该方法入参是Object，所有类型都可以传
        String json2 = JSON.toJSONString(map);
        System.out.println(json2);
    }

    @Test
    public void test3() {
        // String类型的JSON串转list对象
        String json = "[{\"age\":1,\"name\":\"11\"},{\"age\":2,\"name\":\"12\"}]";
        List<Person> list = JSON.parseObject(json, new TypeReference<List<Person>>() {
        });
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("----------------------");

        // String类型的JSON串转map对象
        String json2 = "{1:{\"age\":1,\"name\":\"11\"},2:{\"age\":2,\"name\":\"12\"}}";
        Map<Integer, Person> map = JSON.parseObject(json2, new TypeReference<Map<Integer, Person>>() {
        });
        Set<Map.Entry<Integer, Person>> set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    // Java对象转String类型的JSON串  -------  String类型的JSON串转Java对象
    public void test4() {
        Person p = new Person(1, "11");
        String json = JSON.toJSONString(p);
        System.out.println(json);
        System.out.println("-------------------------");

        //String类型的JSON串转Java对象
        Person p2 = JSONObject.parseObject(json, Person.class);
        System.out.println(p2);
        System.out.println("-------------------------");

        Person p3 = JSON.parseObject(json, Person.class);
        System.out.println(p3);
    }

    @Test
    public void test5() {

        // 取float类型的参数
        String str = "{\"info_quality_last\": 100,\"qualification_industry\": 31,\"level\": 79.5}";
        JSONObject json = JSONObject.parseObject(str);
        Object obj = json.getFloatValue("level");
        // getFloat也可以。getFloatValue和getFloatValue底层调用方法是一样的。
//        Object obj = json.getFloat("level");
        if (obj instanceof Float) {
            System.out.println(obj);
        }
        System.out.println("-------------------------");

        Object obj2 = json.getDoubleValue("level");
        if (obj2 instanceof Float) {
            System.out.println(obj2);
        }
        System.out.println("-------------------------");
    }

}
