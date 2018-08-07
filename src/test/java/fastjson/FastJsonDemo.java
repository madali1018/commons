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

    public static void main(String[] args) {

//        test();
//        test2();
//        test3();
//        test4();
//        test5();
        t();
    }

    private static void t() {

        // fastjson中JSONObject JSONArray的基础使用
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "1");
        jsonObject.put("b", "2");

        JSONArray jsonArray;
        String array = "[{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"1.125\",\"time\": 1508727704},{\"groupid\": \"1.1.1.1\", \"sensorid\": 3, \"pressure\": \"0.842\",\"time\": 1508727704}]";
        jsonArray = JSONArray.parseArray(array);

        jsonObject.put("array", jsonArray);
        System.out.println(jsonArray);
        System.out.println(jsonObject);
        System.out.println("----------------");


        String fieldsStr = "{\"groupid\":\"4.1.1.2.1\",\"sensorid\":2,\"time\":15215484298,\"type\":\"NH3\",\"value\":\"28.50\"}";
        JSONObject fields = JSONObject.parseObject(fieldsStr);
        System.out.println(fields);
        System.out.println(fields.containsKey("time"));
        String s = "" + "time" + "";
        System.out.println(fields.containsKey(s));
    }

    // list对象转String类型的JSON串
    private static void test() {

        List<Person> list = new ArrayList<Person>();
        Person p1 = new Person(1, "11");
        Person p2 = new Person(2, "12");
        list.add(p1);
        list.add(p2);

        String json = JSON.toJSONString(list);
        System.out.println(json);
    }

    // String类型的JSON串转list对象
    private static void test2() {
        //[{"age":1,"name":"11"},{"age":2,"name":"12"}]
        String json = "[{\"age\":1,\"name\":\"11\"},{\"age\":2,\"name\":\"12\"}]";

        List<Person> list2 = JSON.parseObject(json, new TypeReference<List<Person>>() {
        });
        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }
    }

    // map对象转String类型的JSON串
    private static void test3() {
        Person p1 = new Person(1, "11");
        Person p2 = new Person(2, "12");
        Map<Integer, Person> map = new HashMap<>();
        map.put(1, p1);
        map.put(2, p2);

        String json = JSON.toJSONString(map);
        System.out.println(json);
    }

    // String类型的JSON串转map对象
    private static void test4() {
        //{1:{"age":1,"name":"11"},2:{"age":2,"name":"12"}}
        String json = "{1:{\"age\":1,\"name\":\"11\"},2:{\"age\":2,\"name\":\"12\"}}";

        Map<Integer, Person> map = JSON.parseObject(json, new TypeReference<Map<Integer, Person>>() {
        });
        Set<Map.Entry<Integer, Person>> set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    // Java对象转String类型的JSON串  -------  String类型的JSON串转Java对象
    public void test5() {
        Person p = new Person(1, "11");
        String json = JSON.toJSONString(p);
        System.out.println(json);
        System.out.println("-------------------------");

        //String类型的JSON串转Java对象
        Person p2 = JSONObject.parseObject(json, Person.class);
        System.out.println(p2);
    }

}
