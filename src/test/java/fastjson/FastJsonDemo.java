package fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by madali on 2017/11/6.
 */
public class FastJsonDemo {

    public static void main(String[] args) {

        Integer i = 1;
        Long l = i.longValue();

        System.out.println(l);

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

}
