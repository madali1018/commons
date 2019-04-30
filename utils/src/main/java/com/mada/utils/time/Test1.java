package com.mada.utils.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * @Auther: madali
 * @Date: 2018/8/7 10:36
 */
public class Test1 {

    @Test
    public void t1() {
        System.out.println("当前GMT0时间:" + Instant.now());
        System.out.println("当前机器所在时区的时间:" + OffsetDateTime.now());

        //获取当前时间的年
        int year = LocalDate.now().get(ChronoField.YEAR);
        System.out.println(year);

        int dayOfMonth = LocalDate.now().get(ChronoField.DAY_OF_MONTH);
        System.out.println(dayOfMonth);

        int dayOfYear = LocalDate.now().get(ChronoField.DAY_OF_YEAR);
        System.out.println(dayOfYear);
    }

    @Test
    public void t2() {
        String currentTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        System.out.println(currentTimeStr);
    }

    // Date转LocalDate
    @Test
    public void t3() {
        Date date = new Date();
        System.out.println("date2LocalDate:" + date2LocalDate(date));
    }

    // LocalDate转Date
    @Test
    public void t4() {
        LocalDate localDate = LocalDate.now();
        System.out.println("localDate2Date:" + localDate2Date(localDate));
    }

    private static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }

        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

}
