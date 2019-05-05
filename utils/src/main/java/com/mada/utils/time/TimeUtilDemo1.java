package com.mada.utils.time;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;

/**
 * Created by madali on 2019/5/5 10:22
 */
public class TimeUtilDemo1 {

    @Test
    public void t1() {
        System.out.println("当前GMT0时间:" + Instant.now());
        System.out.println("当前GMT0时间毫秒:" + Instant.now().toEpochMilli());
        System.out.println("当前机器所在时区的时间:" + OffsetDateTime.now());

        int year = LocalDate.now().get(ChronoField.YEAR);
        System.out.println("当前年份:" + year);

        int dayOfMonth = LocalDate.now().get(ChronoField.DAY_OF_MONTH);
        System.out.println("今天是本月的第" + dayOfMonth + "天");

        int dayOfYear = LocalDate.now().get(ChronoField.DAY_OF_YEAR);
        System.out.println("今天是本年的第" + dayOfYear + "天");
    }

    private static int timeZone = 8;
    private static String gmt0String = TimeUtil.gmtString(0, TimeUtil.DEFAULT_DATETIME_FORMAT);
    private static String gmtString = TimeUtil.gmtString(timeZone, TimeUtil.DEFAULT_DATETIME_FORMAT);

    @Test
    public void t2() {
        System.out.println("gmt0String:" + gmt0String);
        System.out.println("gmtString:" + gmtString);

        OffsetDateTime gmt0OffsetDateTime = TimeUtil.gmtOffsetDateTime(0);
        OffsetDateTime gmtOffsetDateTime = TimeUtil.gmtOffsetDateTime(timeZone);
        System.out.println("gmt0OffsetDateTime:" + gmt0OffsetDateTime);
        System.out.println("gmtOffsetDateTime:" + gmtOffsetDateTime);
    }

    @Test
    public void t3() {
        //String 转 OffsetDateTime
        OffsetDateTime gmt0OffsetDateTime = TimeUtil.stringToOffsetDateTime(gmt0String, 0, TimeUtil.DEFAULT_DATETIME_FORMAT);
        OffsetDateTime gmtOffsetDateTime = TimeUtil.stringToOffsetDateTime(gmtString, timeZone, TimeUtil.DEFAULT_DATETIME_FORMAT);
        System.out.println("gmt0OffsetDateTime:" + gmt0OffsetDateTime);
        System.out.println("gmtOffsetDateTime:" + gmtOffsetDateTime);

        //OffsetDateTime 转 String
        String gmt0String = TimeUtil.offsetDateTimeToString(gmt0OffsetDateTime, TimeUtil.DEFAULT_DATETIME_FORMAT);
        String gmtString = TimeUtil.offsetDateTimeToString(gmtOffsetDateTime, TimeUtil.DEFAULT_DATETIME_FORMAT);
        System.out.println("gmt0String:" + gmt0String);
        System.out.println("gmtString:" + gmtString);

        //GMT0 转成 GMT
        OffsetDateTime offsetDateTimeGMT = TimeUtil.convertTo(gmt0OffsetDateTime, timeZone);
        System.out.println("offsetDateTimeGMT:" + offsetDateTimeGMT);

        //GMT 转成 GMT0
        OffsetDateTime offsetDateTimeGMT0 = TimeUtil.convertTo(offsetDateTimeGMT, 0);
        System.out.println("offsetDateTimeGMT0:" + offsetDateTimeGMT0);
    }

    @Test
    public void t4() {
        String gmt0Str = TimeUtil.gmtString(0, TimeUtil.DEFAULT_DATE_FORMAT);
        String gmtStr = TimeUtil.gmtString(timeZone, TimeUtil.DEFAULT_DATE_FORMAT);

        String gmt0Str2 = TimeUtil.gmtString(0, TimeUtil.DEFAULT_DATETIME_FORMAT);
        String gmtStr2 = TimeUtil.gmtString(timeZone, TimeUtil.DEFAULT_DATETIME_FORMAT);

        //比较日期
        System.out.println(TimeUtil.compareLocalDate(gmt0Str, gmtStr));
        //比较时间
        System.out.println(TimeUtil.compareLocalDateTime(gmt0Str2, gmtStr2));
    }

    @Test
    public void t5() {
        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime firstDayInCurrentMonthODT = TimeUtil.getFirstDayInCurrentMonth(now, timeZone);
        OffsetDateTime lastDayInCurrentMonthODT = TimeUtil.getLastDayInCurrentMonth(now, timeZone);
        OffsetDateTime lastDayInPreviousMonthODT = TimeUtil.getLastDayInPreviousMonth(now, timeZone);
        System.out.println("当月第一天:" + firstDayInCurrentMonthODT);
        System.out.println("当月最后一天:" + lastDayInCurrentMonthODT);
        System.out.println("上一个月最后一天:" + lastDayInPreviousMonthODT);

        System.out.println("当前时间微秒:" + TimeUtil.getCurrentGmt0Datetime("2019-05-05 01:35:39"));
    }

    @Test
    public void t6() {
        String t1 = "2019-05-05 01:35:39";
        String t2 = "2018-03-04 00:25:19";
        OffsetDateTime offsetDateTime1 = TimeUtil.stringToOffsetDateTime(t1, 0, TimeUtil.DEFAULT_DATETIME_FORMAT);
        OffsetDateTime offsetDateTime2 = TimeUtil.stringToOffsetDateTime(t2, 0,TimeUtil. DEFAULT_DATETIME_FORMAT);

        long between = offsetDateTime1.toEpochSecond() - offsetDateTime2.toEpochSecond();//时间差，单位为秒
        long days = between / (24 * 3600);
        long hours = (between - days * 24 * 3600) / 3600;
        long minutes = (between - days * 24 * 3600 - hours * 3600) / 60;
        long seconds = between % 60;

        StringBuilder timeDifference = new StringBuilder();
        timeDifference.append("Time Difference: ");

        if (days > 0) {
            timeDifference.append(days).append("days, ");
        }
        if (hours > 0) {
            timeDifference.append(hours).append("hours, ");
        }
        if (minutes > 0) {
            timeDifference.append(minutes).append("minutes, ");
        }

        timeDifference.append(seconds).append("seconds.");

        System.out.println("两个GMT0时间的差值:" + timeDifference.toString());
    }

}
