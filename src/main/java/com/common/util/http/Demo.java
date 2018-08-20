package com.common.util.http;

/**
 * @Auther: madali
 * @Date: 2018/8/16 11:10
 */
public class Demo {

    private static String url = "http://site-api.anjuke.test/broker/getLevelInfo/c6fc09d70cb3174eaaf4e2ba1e5b740b/?broker_id=1170239";

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

}
