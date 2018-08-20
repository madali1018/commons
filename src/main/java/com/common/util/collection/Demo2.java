package com.common.util.collection;

import org.junit.Test;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: madali
 * @Date: 2018/8/20 13:58
 */
public class Demo2 {

    private static Hashtable hashtable = new Hashtable();

    static {
        hashtable.put("1", "111");
        hashtable.put("2", "222");
        hashtable.put("3", "333");
        hashtable.put("4", "444");
    }

    @Test
    public void test1() {
        //通过Enumeration遍历Hashtable的键
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }

        System.out.println("----------------");

        //通过Enumeration遍历Hashtable的值
        Enumeration enumeration2 = hashtable.elements();
        while (enumeration2.hasMoreElements()) {
            System.out.println(enumeration2.nextElement());
        }

        System.out.println("----------------");
        hashtable.forEach((key, value) -> System.out.println(key + ", " + value));

        System.out.println("----------------");
        Set<Map.Entry> set = hashtable.entrySet();
        for (Map.Entry entry : set) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    @Test
    public void test2() {
        //返回值为要删除的那个元素的value
        Object object = hashtable.remove("1");
        System.out.println(object);
    }

}
