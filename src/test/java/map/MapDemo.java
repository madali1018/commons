package map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by madali on 2018/1/23.
 */
public class MapDemo {

    public static void main(String[] args) {

//        String fieldsStr = "{\"groupid\":\"4.1.1.2.1\",\"sensorid\":2,\"time\":15215484298,\"type\":\"NH3\",\"value\":\"28.50\"}";
//        JSONObject fields = JSONObject.parseObject(fieldsStr);
//        System.out.println(fields);

        Map map = new HashMap();
        map.put("111", 1);
        map.put("222", 2);
        map.put("333", 3);

        JSONObject json = (JSONObject) JSON.toJSON(map);
        System.out.println(json);
    }

}
