package com.mada.commons.util.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by madali on 2018/1/1.
 */
public class TimeUtil2 {

    private static final String PATTERN = "YYYY-MM-dd HH:mm:ss";

    /**
     * 获取当前时间加减几分钟后的时间
     *
     * @param minutes
     * @return
     */
    public static String getAdditionTime(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);// 24小时制   
        return new SimpleDateFormat(PATTERN).format(calendar.getTime());
    }

    /**
     * 判断一个Long型时间戳（秒10位，毫秒13位）与当前时间的差值是否在24小时内
     * <p>
     * 1. 暂时认为time是合法的秒或毫秒，不做校验。
     *
     * @param time
     * @return
     */
    public static boolean isLegal(Long time) {

        boolean flag = false;

        Long currentTime = System.currentTimeMillis();
        //前一天时间（毫秒）
        Long previousTime = currentTime - 24 * 60 * 60 * 1000L;

        if (Long.toString(time).length() == 10) {
            if ((time * 1000L) >= previousTime && (time * 1000L) <= currentTime) {
                flag = true;
            }
        } else if (Long.toString(time).length() == 13) {
            if (time >= previousTime && time <= currentTime) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 将Long型时间戳（调用isLegal返回为true的合法时间戳）转换为Date
     * <p>
     * 1. 暂时认为time是合法的秒或毫秒，不做校验。
     *
     * @param time
     * @return
     */
    public static Date convertDate(Long time) {

        Calendar calendar = Calendar.getInstance();

        //将秒转换为毫秒
        if (Long.toString(time).length() == 10) {
            time = time * 1000L;
        }

        calendar.setTimeInMillis(time);

        return calendar.getTime();
    }

    public static void main(String[] args) {

        System.out.println(getAdditionTime(-10));
        System.out.println(getAdditionTime(10));

        System.out.println(isLegal(1514805297L));
        System.out.println(isLegal(1514805300000L));
        System.out.println(isLegal(1514805297L));
        System.out.println(isLegal(1514892900000L));

        System.out.println(convertDate(1514805297L));
        System.out.println(convertDate(1514805300000L));
    }

}
