package com.mada.commons.util.http;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @Auther: madali
 * @Date: 2018/8/16 11:10
 */
public class Demo {

    private static String url = "http://site-api.anjuke.test/broker/getLevelInfo/c6fc09d70cb3174eaaf4e2ba1e5b740b/?broker_id=1170239";
//    private static String url = "https://www.baidu.com/";

    @org.junit.Test
    public void test1() {
        String getResult = HttpClientUtil.sendGet(url);
        System.out.println(getResult);
        System.out.println("------------------------------------------");
        String postResult = HttpClientUtil.sendPost(url);
        System.out.println(postResult);
    }

    @org.junit.Test
    public void test2() {
        String getResult = HttpClientUtil2.sendGet(url);
        System.out.println(getResult);
        System.out.println("------------------------------------------");
        String postResult = HttpClientUtil2.sendPost(url);
        System.out.println(postResult);
    }

    @Test
    public void test3() {
        String url = "http://localhost:8080/index/person/p3";
        String param = "username=name3&pwd=1";
        String result = HttpClientUtil2.sendPost(url, param);
        System.out.println(result);
    }

    @Test
    public void test4() {
        String url = "http://localhost:8080/index/person/p4";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "name4");

        String result = HttpClientUtil2.sendPost(url, jsonObject.toJSONString());
        System.out.println(result);
    }

}
