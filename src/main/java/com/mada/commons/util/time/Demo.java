package com.mada.commons.util.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * @Auther: madali
 * @Date: 2018/8/7 10:36
 */
public class Demo {

    @Test
    public void test1() {
        //获取当前时间的年
        int year = LocalDate.now().get(ChronoField.YEAR);
        System.out.println(year);

        int dayOfMonth = LocalDate.now().get(ChronoField.DAY_OF_MONTH);
        System.out.println(dayOfMonth);

        int dayOfYear = LocalDate.now().get(ChronoField.DAY_OF_YEAR);
        System.out.println(dayOfYear);
    }

    @Test
    public void test2() {
        String currentTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        System.out.println(currentTimeStr);
    }
}
