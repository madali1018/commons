package com.mada.commons.demo.equalsdemo;

/**
 * Created by madali on 2018/12/1 17:02
 */
public class EqualsDemo {

    public static void main(String[] args) {

        // 1.八种基本数据类型：只能用==来比较，不能用equals（编译不通过），比较的是其存储的 “值”是否相等。
        int a = 10;
        int b = 10;
        System.out.println(a == b);
        System.out.println("============");

        // 2.八种基本类型的包装类：==是比较地址的，equal是比较内容的。
        Integer i1 = 100;
        Integer i2 = 100;
        System.out.println(i1.equals(i2));
        System.out.println(i1 == i2);
        System.out.println("============");

        // 3.String：equals方法比较内容（String重写了equals方法），而==比较指向的对象是否相同（即对象在内存中的首地址是否相同）。
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);

        String s3 = new String("abc");
        String s4 = new String("abc");
        System.out.println(s3.equals(s4));
        System.out.println(s3 == s4);

        System.out.println(s1.equals(s3));
        System.out.println(s1 == s3);
        System.out.println("============");

        // 4.StringBuilder StringBuffer
        StringBuilder stringBuilder1 = new StringBuilder("a");
        StringBuilder stringBuilder2 = new StringBuilder("a");
        System.out.println(stringBuilder1.equals(stringBuilder2));
        System.out.println(stringBuilder1 == stringBuilder2);

        StringBuffer stringBuffer1 = new StringBuffer("b");
        StringBuffer stringBuffer2 = new StringBuffer("b");
        System.out.println(stringBuffer1.equals(stringBuffer2));
        System.out.println(stringBuffer1 == stringBuffer2);

        // 5.引用类型（即对象）：如果没有对equals方法进行重写，则比较的是引用类型的变量所指向的对象的地址；诸如String、Date等类对equals方法进行了重写的话，比较的是所指向的对象的内容。
    }

}
