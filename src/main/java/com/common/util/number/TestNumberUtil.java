package com.common.util.number;

/**
 * @Auther: madali
 * @Date: 2018/7/6 16:08
 */
public class TestNumberUtil {

    public static void main(String[] args) {

        System.out.println(NumberUtil.round(12.541D, 2));
        System.out.println(NumberUtil.round(12.0001D, 2));
        System.out.println(NumberUtil.round(12.00D, 2));
        System.out.println(NumberUtil.round(12.0D, 2));

        System.out.println("--------------------------");
        System.out.println(NumberUtil.trimZero("10.510"));
        System.out.println(NumberUtil.trimZero("10.50"));
        System.out.println(NumberUtil.trimZero("10.000"));
        System.out.println(NumberUtil.trimZero("10.0"));
        System.out.println(NumberUtil.trimZero("10.51974"));
        System.out.println(NumberUtil.trimZero("1080.120"));
        System.out.println(NumberUtil.trimZero("1080.10"));
        System.out.println(NumberUtil.trimZero("1080.0"));
        System.out.println(NumberUtil.trimZero("1080.01"));
        System.out.println(NumberUtil.trimZero("1080"));
        System.out.println(NumberUtil.trimZero("1000"));
        System.out.println("--------------------------");

        System.out.println(NumberUtil.trimNumber("12.45411"));
        System.out.println(NumberUtil.trimNumber("12.0001"));
        System.out.println(NumberUtil.trimNumber("12.0"));
        System.out.println(NumberUtil.trimNumber("12.01"));
        System.out.println(NumberUtil.trimNumber("1201"));
        System.out.println(NumberUtil.trimNumber("12010"));
    }

}
