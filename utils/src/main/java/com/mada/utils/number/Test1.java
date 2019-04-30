package com.mada.utils.number;

import org.junit.Test;

/**
 * @Auther: madali
 * @Date: 2018/7/6 16:08
 */
public class Test1 {

    @Test
    public void t1() {
        System.out.println(Long.MAX_VALUE);

        for (int i = 0; i < 12; i++) {
            System.out.println(NumberUtil.n32nMultipy(i));
        }

        System.out.println(NumberUtil.base32To10Number("wx4g0u0p5jhq"));
        System.out.println(NumberUtil.base32To10Number("wx4g0wp5sgp6"));
        System.out.println(NumberUtil.base32To10Number("wx4g0wp5krty"));
        System.out.println(NumberUtil.base32To10Number("wx4g0wp5tw2v"));
        System.out.println(NumberUtil.base32To10Number("ybkfc7cw43zt"));
    }

    public void t2() {
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
