package com.mada.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @Auther: madali
 * @Date: 2018/8/16 11:10
 */
public class HttpClientUtilDemo {

    @Test
    public void t1() {
        String url = "https://www.baidu.com/";

        String result = HttpClientUtil.get(url);
        System.out.println(result);

        String result2 = HttpClientUtil.post(url);
        System.out.println(result2);

        String result3 = HttpClientUtil2.sendGet(url);
        System.out.println(result3);

        String result4 = HttpClientUtil2.sendPost(url);
        System.out.println(result4);
    }

    @Test
    public void t2() {
        String url = "http://localhost:8080/index/person/p4";
        String param = "username=name3&pwd=1";

        String result = HttpClientUtil2.sendPost(url, param);
        System.out.println(result);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "name4");

        String result2 = HttpClientUtil2.sendPost(url, jsonObject.toJSONString());
        System.out.println(result2);
    }

}
