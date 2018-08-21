package com.common.demo.java8;

import org.junit.Test;

import java.util.Objects;

/**
 * Created by madali on 2018/3/20.
 */
public class ObjectsDemo {

    @Test
    public void test1() {
        StringBuilder s1 = new StringBuilder("a");
        StringBuilder s2 = new StringBuilder("a");
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);

        StringBuffer s3 = new StringBuffer("b");
        StringBuffer s4 = new StringBuffer("b");
        System.out.println(s3.equals(s4));
        System.out.println(s3 == s4);

        String s5 = "c";
        String s6 = "c";
        System.out.println(s5.equals(s6));
        System.out.println(s5 == s6);
    }

    @Test
    public void equalsTest() {
        String str1 = "hello";
        String str2 = "hello";
        //传入对象
        boolean equals = Objects.equals(str1, str2);
        System.out.println("Objects.equals(str1, str2) ?  " + equals);
    }

    @Test
    public void deepEqualsTest() {
        String str1 = "hello";
        String str2 = "hello";
        //传入对象
        boolean deepEquals = Objects.deepEquals(str1, str2);
        System.out.println("Objects.deepEquals(str1, str2) ?  " + deepEquals);
        int[] arr1 = {1, 2};
        int[] arr2 = {1, 2};
        //传入数组 深度比较两个对象是否相等(首先比较内存地址,相同返回true;如果传入的是数组，则比较数组内的对应下标值是否相同)
        deepEquals = Objects.deepEquals(arr1, arr2);
        System.out.println("Objects.deepEquals(arr1, arr2) ?  " + deepEquals);
    }

    @Test
    public void hashCodeTest() {
        String str1 = "hello";

        //传入对象
        int hashCode = Objects.hashCode(str1);
        System.out.println("Objects.hashCode(str1) ?  " + hashCode);

        //传入null
        hashCode = Objects.hashCode(null);
        System.out.println("Objects.hashCode(null) ?  " + hashCode);
    }

    @Test
    public void hashTest() {
        int a = 100;

        //传入对象
        int hashCode = Objects.hashCode(a);
        System.out.println("Objects.hashCode(str1) ?  " + hashCode);

        //输入数组
        int[] arr = {100, 100};
        hashCode = Objects.hash(arr);
        System.out.println("Objects.hashCode(arr) ?  " + hashCode);
    }

    @Test
    public void compareTest() {
        int a = 100;
        int b = 1000;
        // 使用指定的比较器c 比较参数a和参数b的大小（相等返回0，a大于b返回正数1，a小于b返回负数-1）
        int compare = Objects.compare(a, b, Integer::compareTo);
        System.out.println(" compare = " + compare);
    }

    @Test
    public void requireNonNullTest() {
        String test = null;
        //java.lang.NullPointerException
        String s1 = Objects.requireNonNull(test);

        //java.lang.NullPointerException: 这是空指针异常提示的信息
//        String s2 = Objects.requireNonNull(test, "这是空指针异常提示的信息");

        //java.lang.NullPointerException: 我是返回的异常信息
//        String s3 = Objects.requireNonNull(test, "我是返回的异常信息");
    }

    @Test
    public void nullTest() {
        Object obj = null;
        System.out.println(Objects.isNull(obj) + "," + Objects.nonNull(obj));
    }

}
