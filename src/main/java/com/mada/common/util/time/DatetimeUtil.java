package com.mada.common.util.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by madali on 2017/5/2.
 */
public class DatetimeUtil {

    public static final long MILLISECOND_OF_HOUR = 60 * 60 * 1000;
    public static final int HOUR_OF_DAY = 24;
    public static final int DAY_OF_WEEK = 7;
    public static final int MONTH_OF_YEAR = 12;
    public static final long MILLISECOND_OF_DAY = MILLISECOND_OF_HOUR * HOUR_OF_DAY;

    /**
     * 获取GMT0时区的当前毫秒时间
     *
     * @return
     */
    public static long getGMT0TimeMillis() {

        return Instant.now().toEpochMilli();
    }

    /**
     * 获取GMT0时区的毫秒时间
     *
     * @return
     */
    public static long getGMT0TimeMillis(long gmtTimeMillis, int timeZone) {

        return gmtTimeMillis - (MILLISECOND_OF_HOUR * timeZone);
    }

    /**
     * 根据GMT0和时区，获取指定时区的毫秒时间
     *
     * @param gmt0TimeMillis
     * @param timeZone
     * @return
     */
    public static long getGMTTimeMillis(long gmt0TimeMillis, int timeZone) {

        return gmt0TimeMillis + (MILLISECOND_OF_HOUR * timeZone);
    }

    /**
     * 获取指定时区和时间所在周
     *
     * @param gmtTimeMillis
     * @param timeZone
     * @return
     */
    public static int getWeek(long gmtTimeMillis, int timeZone) {

        long gmt0TimeMillis = getGMT0TimeMillis(gmtTimeMillis, timeZone);

        Instant instant = Instant.ofEpochMilli(gmt0TimeMillis);
        ZoneId zoneId = ZoneId.of(formatTimeZone(timeZone));

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        return (dayOfWeek == DayOfWeek.SUNDAY) ? 0 : dayOfWeek.getValue();
    }

    /**
     * 获取日期
     *
     * @param gmtTimeMillis
     * @return
     */
    public static LocalDate getDate(long gmtTimeMillis) {

        return LocalDate.ofEpochDay(gmtTimeMillis / MILLISECOND_OF_DAY);
    }

    /**
     * 根据日差，获取日期
     *
     * @param deltaDay
     * @param gmtTimeMillis
     * @return
     */
    public static LocalDate plusDays(int deltaDay, long gmtTimeMillis) {

        return plusDays(deltaDay, getDate(gmtTimeMillis));
    }

    /**
     * 根据日差，获取日期
     *
     * @param deltaDay
     * @param date
     * @return
     */
    public static LocalDate plusDays(int deltaDay, LocalDate date) {

        return date.plusDays(deltaDay);
    }

    /**
     * 字符串转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate getDate(String date) {

        int spaceIndex = date.indexOf(" ");
        if (spaceIndex != -1)
            date = date.substring(0, spaceIndex);

        String[] arr = date.split("-");

        return LocalDate.of(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
    }

    /**
     * 根据日期获取毫秒
     *
     * @param date
     * @return
     */
    public static long getGMTTimeMillis(LocalDate date) {

        return date.toEpochDay() * MILLISECOND_OF_DAY;
    }

    /**
     * 根据月差，获取日期
     *
     * @param deltaMonth
     * @param gmtTimeMillis
     * @return
     */
    public static LocalDate plusMonths(int deltaMonth, long gmtTimeMillis) {

        return getDate(gmtTimeMillis).plusMonths(deltaMonth);
    }

    /**
     * 字符串转毫秒
     *
     * @param datetime
     * @return
     */
    public static long getGMTTimeMillis(String datetime) {

        if (!datetime.contains(" "))
            datetime += " 00:00:00";

        String[] arr = datetime.split(" ");

        return getGMTTimeMillis(arr[0], formatTime(arr[1]));
    }

    /**
     * 字符串转毫秒
     *
     * @param date
     * @param time
     * @return
     */
    public static long getGMTTimeMillis(String date, String time) {

        return getGMTTimeMillis(getDate(date), time);
    }

    /**
     * 根据日期和时间获取毫秒时间
     *
     * @param date
     * @param time
     * @return
     */
    public static long getGMTTimeMillis(LocalDate date, String time) {

        time = formatTime(time);

        String[] arr = time.split(":");

        Double s = Double.valueOf(arr[2]);

        int hour = Integer.valueOf(arr[0]);
        int minute = Integer.valueOf(arr[1]);
        int second = s.intValue();
        int nanoOfSecond = (int) ((s - second) * Math.pow(10, 9));

        LocalTime localTime = LocalTime.of(hour, minute, second, nanoOfSecond);

        return date.toEpochDay() * MILLISECOND_OF_DAY + localTime.toNanoOfDay() / (long) Math.pow(10, 6);
    }

    /**
     * 格式化时区
     *
     * @param timeZone
     * @return
     */
    public static String formatTimeZone(int timeZone) {

        return "GMT" + (timeZone >= 0 ? ("+" + timeZone) : timeZone);
    }

    /**
     * 获取指定时区和时间所在月最后一日
     *
     * @param gmtTimeMillis
     * @return
     */
    public static int getLastDayOfMonth(long gmtTimeMillis) {

        LocalDate date = getDate(gmtTimeMillis);

        return LocalDate.of(date.getYear(), date.getMonth().plus(1), 1).minusDays(1).getDayOfMonth();
    }

    /**
     * 获取日
     *
     * @param gmtTimeMillis
     * @return
     */
    public static int getDayOfMonth(long gmtTimeMillis) {

        return getDate(gmtTimeMillis).getDayOfMonth();
    }

    /**
     * 获取日差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDeltaDay(LocalDate date1, LocalDate date2) {

        return date2.toEpochDay() - date1.toEpochDay();
    }

    /**
     * @param gmt0TimeMillis
     * @param timeZone
     * @return
     */
    public static String getDatetime(long gmt0TimeMillis, int timeZone) {

        Instant instant = Instant.ofEpochMilli(gmt0TimeMillis);
        ZoneId zoneId = ZoneId.of(formatTimeZone(timeZone));

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

//        String datetime = localDateTime.toLocalDate().toString() + " " + localDateTime.toLocalTime().toString();
        String datetime = localDateTime.toLocalDate().toString() + " " + localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return datetime;
    }

    public static String formatDate(LocalDate date, String format) {

        return formatDate(getGMTTimeMillis(date), format);
    }

    public static String formatDate(long gmt0TimeMillis, String dateFormat) {

        return getDate(gmt0TimeMillis).format(DateTimeFormatter.ofPattern(dateFormat, new Locale("en")));
    }

    public static String formatDate(String date, String format) {

        return formatDate(getGMTTimeMillis(date), format);
    }

    public static String formatDatetime(long gmt0TimeMillis, int timeZone, String dateFormat) {

        String datetime = getDatetime(gmt0TimeMillis, timeZone);

        int dotIndex = datetime.indexOf(".");
        if (dotIndex != -1)
            datetime = datetime.substring(0, dotIndex);

        String[] sArr = datetime.split(" ");

        return formatDate(sArr[0], dateFormat) + " " + sArr[1];
    }

    public static String formatTime(String time) {

        switch (time.split(":").length) {
            case 1:
                time += ":00:00"; //补充分秒位

                break;
            case 2:
                time += ":00"; //补充秒位

                break;
        }

        return time;
    }

    public static void main(String[] args) {

        //Instant.now()获取当前GMT0时间，OffsetDateTime.now()获取的是当前机器所在时区的时间
        System.out.println(Instant.now());
        System.out.println(OffsetDateTime.now());
        System.out.println(Instant.now().toEpochMilli());
    }
}
