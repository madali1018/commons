package com.common.util.time;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 * Created by madl on 2017/4/26.
 */
public class TimeUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = DEFAULT_DATE_FORMAT + " HH:mm:ss";

    //获取当前时刻（含时区）
    public static String gmtDatetime(int timeZone, String format) {

        return OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.ofHours(timeZone)).format(DateTimeFormatter.ofPattern(format));
    }

    //获取当前时刻（含时区）
    public static OffsetDateTime gmtOffsetDateTime(int timeZone) {

        return OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.ofHours(timeZone));
    }

    //String 转 OffsetDatetime(含时区)
    public static OffsetDateTime offsetDateTime(String gmtDatetime, int timeZone) {

        return offsetDateTime(gmtDatetime, timeZone, DEFAULT_DATETIME_FORMAT);
    }

    //String 转 OffsetDatetime(含时区)
    public static OffsetDateTime offsetDateTime(String gmtDatetime, int timeZone, String format) {

        return LocalDateTime.parse(gmtDatetime, DateTimeFormatter.ofPattern(format)).atOffset(ZoneOffset.ofHours(timeZone));
    }

    //OffsetDateTime 转 String
    public static String convertToString(OffsetDateTime offsetDateTime, String format) {

        return offsetDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    //GMT0 和 GMT 互转  timeZone的值为要转成的那个时区的timeZone
    public static OffsetDateTime convertTo(OffsetDateTime offsetDateTime, int timeZone) {

        return offsetDateTime.withOffsetSameInstant(ZoneOffset.ofHours(timeZone));
    }

    //获取当前月份的第一天
    public static OffsetDateTime getFirstDayInCurrentMonth(OffsetDateTime offsetDateTime, int timeZone) {

        return OffsetDateTime.of(offsetDateTime.getYear(), offsetDateTime.getMonth().getValue(), 1, 0, 0, 0, 0, ZoneOffset.ofHours(timeZone));
    }

    public static int compareDate(String t1, String t2) {
        return compareDate(t1, t2, DEFAULT_DATE_FORMAT);
    }

    //比较两个日期
    public static int compareDate(String t1, String t2, String format) {
        LocalDate localDate1 = LocalDate.parse(t1, DateTimeFormatter.ofPattern(format));
        LocalDate localDate2 = LocalDate.parse(t2, DateTimeFormatter.ofPattern(format));

        return localDate1.compareTo(localDate2);
    }

    public static int compareDatetime(String t1, String t2) {
        return compareDatetime(t1, t2, DEFAULT_DATETIME_FORMAT);
    }

    //比较两个时间
    public static int compareDatetime(String t1, String t2, String format) {
        LocalDateTime localDateTime1 = LocalDateTime.parse(t1, DateTimeFormatter.ofPattern(format));
        LocalDateTime localDateTime2 = LocalDateTime.parse(t2, DateTimeFormatter.ofPattern(format));

        return localDateTime1.compareTo(localDateTime2);
    }

    //获取当前月份的最后一天（当月第一天加一个月再减去一天）
    public static OffsetDateTime getLastDayInCurrentMonth(OffsetDateTime offsetDateTime, int timeZone) {

        return getFirstDayInCurrentMonth(offsetDateTime, timeZone).plusMonths(1).minusDays(1);
    }

    //获取上月最后一天（当前月第一天减一天）
    public static OffsetDateTime getLastDayInPreviousMonth(OffsetDateTime offsetDateTime, int timeZone) {

        return getFirstDayInCurrentMonth(offsetDateTime, timeZone).minusDays(1);
    }

    public static void main(String[] args) {

        int timeZone = 8;

        String gmt0Datetime = gmtDatetime(0, DEFAULT_DATETIME_FORMAT);
        String gmtDatetime = gmtDatetime(timeZone, DEFAULT_DATETIME_FORMAT);
        System.out.println("gmt0Datetime: " + gmt0Datetime);
        System.out.println("gmtDatetime: " + gmtDatetime);
        System.out.println("--------------------");

        OffsetDateTime gmt0OffsetDateTime = gmtOffsetDateTime(0);
        OffsetDateTime gmtOffsetDateTime = gmtOffsetDateTime(timeZone);
        System.out.println("gmt0OffsetDateTime: " + gmt0OffsetDateTime);
        System.out.println("gmtOffsetDateTime: " + gmtOffsetDateTime);
        System.out.println("--------------------");


        //String 转 OffsetDateTime
        OffsetDateTime gmt0DatetimeODT = offsetDateTime(gmt0Datetime, 0);
        OffsetDateTime gmtDatetimeODT = offsetDateTime(gmtDatetime, timeZone);
        System.out.println("gmt0DatetimeODT: " + gmt0DatetimeODT);
        System.out.println("gmtDatetimeODT: " + gmtDatetimeODT);
        System.out.println("--------------------");

        //OffsetDateTime 转 String
        String gmt0DatetimeStr = convertToString(gmt0DatetimeODT, DEFAULT_DATETIME_FORMAT);
        String gmtDatetimeStr = convertToString(gmtDatetimeODT, DEFAULT_DATETIME_FORMAT);
        System.out.println("gmt0DatetimeStr: " + gmt0DatetimeStr);
        System.out.println("gmtDatetimeStr: " + gmtDatetimeStr);
        System.out.println("--------------------");

        //GMT0 转成 GMT
        OffsetDateTime offsetDateTimeGMT = convertTo(gmt0DatetimeODT, timeZone);
        //GMT 转成 GMT0
        OffsetDateTime offsetDateTimeGMT0 = convertTo(gmtDatetimeODT, 0);
        System.out.println("offsetDateTimeGMT: " + offsetDateTimeGMT);
        System.out.println("offsetDateTimeGMT0: " + offsetDateTimeGMT0);
        System.out.println("--------------------");

        //比较日期，时间
        String gmt0Date = gmtDatetime(0, DEFAULT_DATE_FORMAT);
        String gmtDate = gmtDatetime(timeZone, DEFAULT_DATE_FORMAT);
        System.out.println(compareDate(gmt0Date, gmtDate));
        System.out.println(compareDatetime(gmt0Datetime, gmtDatetime));
        System.out.println("--------------------");

        OffsetDateTime firstDayInCurrentMonthODT = getFirstDayInCurrentMonth(offsetDateTimeGMT, timeZone);
        OffsetDateTime lastDayInCurrentMonthODT = getLastDayInCurrentMonth(offsetDateTimeGMT, timeZone);
        OffsetDateTime lastDayInPreviousMonthODT = getLastDayInPreviousMonth(offsetDateTimeGMT, timeZone);
        System.out.println("firstDayInCurrentMonthODT: " + firstDayInCurrentMonthODT);
        System.out.println("lastDayInCurrentMonthODT: " + lastDayInCurrentMonthODT);
        System.out.println("lastDayInPreviousMonthODT: " + lastDayInPreviousMonthODT);
    }
}
