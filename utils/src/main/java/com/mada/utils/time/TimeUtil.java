package com.mada.utils.time;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 * Created by madali on 2017/4/26.
 */
public class TimeUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = DEFAULT_DATE_FORMAT + " HH:mm:ss";

    /**
     * 获取当前时刻（含时区）
     * 1.Instant.now()获取当前GMT0时间
     * 2.OffsetDateTime.now()获取的是当前机器所在时区的时间
     *
     * @param timeZone
     * @param format
     * @return
     */
    public static String gmtString(int timeZone, String format) {
        return gmtOffsetDateTime(timeZone).format(DateTimeFormatter.ofPattern(format));
    }

    //获取当前时刻（含时区）
    public static OffsetDateTime gmtOffsetDateTime(int timeZone) {
        return OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.ofHours(timeZone));
    }

    //String 转 OffsetDatetime(含时区)
    public static OffsetDateTime stringToOffsetDateTime(String gmtDatetime, int timeZone, String format) {
        return LocalDateTime.parse(gmtDatetime, DateTimeFormatter.ofPattern(format)).atOffset(ZoneOffset.ofHours(timeZone));
    }

    //OffsetDateTime 转 String
    public static String offsetDateTimeToString(OffsetDateTime offsetDateTime, String format) {
        return offsetDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * GMT0 和 GMT 互转
     *
     * @param offsetDateTime
     * @param timeZone       timeZone的值为要转成的那个时区的timeZone
     * @return
     */
    public static OffsetDateTime convertTo(OffsetDateTime offsetDateTime, int timeZone) {
        return offsetDateTime.withOffsetSameInstant(ZoneOffset.ofHours(timeZone));
    }

    public static int compareLocalDate(String t1, String t2) {
        return compareLocalDate(t1, t2, DEFAULT_DATE_FORMAT);
    }

    //比较两个日期
    public static int compareLocalDate(String t1, String t2, String format) {
        LocalDate localDate1 = LocalDate.parse(t1, DateTimeFormatter.ofPattern(format));
        LocalDate localDate2 = LocalDate.parse(t2, DateTimeFormatter.ofPattern(format));

        return localDate1.compareTo(localDate2);
    }

    public static int compareLocalDateTime(String t1, String t2) {
        return compareLocalDateTime(t1, t2, DEFAULT_DATETIME_FORMAT);
    }

    //比较两个时间
    public static int compareLocalDateTime(String t1, String t2, String format) {
        LocalDateTime localDateTime1 = LocalDateTime.parse(t1, DateTimeFormatter.ofPattern(format));
        LocalDateTime localDateTime2 = LocalDateTime.parse(t2, DateTimeFormatter.ofPattern(format));

        return localDateTime1.compareTo(localDateTime2);
    }

    //获取当前月份的第一天
    public static OffsetDateTime getFirstDayInCurrentMonth(OffsetDateTime offsetDateTime, int timeZone) {
        return OffsetDateTime.of(offsetDateTime.getYear(), offsetDateTime.getMonth().getValue(), 1, 0, 0, 0, 0, ZoneOffset.ofHours(timeZone));
    }

    //获取当前月份的最后一天（当月第一天加一个月再减去一天）
    public static OffsetDateTime getLastDayInCurrentMonth(OffsetDateTime offsetDateTime, int timeZone) {
        return getFirstDayInCurrentMonth(offsetDateTime, timeZone).plusMonths(1).minusDays(1);
    }

    //获取上月最后一天（当前月第一天减一天）
    public static OffsetDateTime getLastDayInPreviousMonth(OffsetDateTime offsetDateTime, int timeZone) {
        return getFirstDayInCurrentMonth(offsetDateTime, timeZone).minusDays(1);
    }

    //获取当前时间（精确到微秒）
    public static String getCurrentGmt0Datetime(String currentGmt0Datetime) {
        String nanoStr = Long.toString(System.nanoTime());

        StringBuffer sb = new StringBuffer();
        sb.append(currentGmt0Datetime);
        sb.append(".");
        sb.append(nanoStr, 5, 11);

        return sb.toString();
    }

}
