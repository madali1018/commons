package com.mada.common.util.serializable;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * Created by madali on 2018/11/28 16:40
 */
public class Test {

    String filename = "person.txt";

    // fastjson序列化，反序列化
    @org.junit.Test
    public void t1() throws IOException, ClassNotFoundException {
        Person p1 = new Person("admin", "123456");
        String str = JSONObject.toJSONString(p1);
        System.out.println(str);

        Person p2 = JSONObject.parseObject(String.valueOf(str), Person.class);
        System.out.println(p2);
    }

    @org.junit.Test
    public void t2() {
        Person p1 = new Person("admin", "123456");
        JavaSerializable.serializable(p1, filename);
        System.out.println("序列化结束");

        Person p2 = JavaSerializable.deSerializable(Person.class, filename);
        System.out.println(p2);
    }

}
