package com.common.util.time;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by madali on 2018/1/1.
 */
public class TimeUtil2 {

    /**
     * 判断一个Long型时间戳（秒10位，毫秒13位）与当前时间的差值是否在24小时内
     *
     * @param time
     * @return
     */
    public static Boolean isLegal(Long time) {

        Boolean flag = false;

        Long currentTime = System.currentTimeMillis();
        //前一天时间（毫秒）
        Long previousTime = currentTime - 24 * 60 * 60 * 1000L;

        if (String.valueOf(time).length() == 10) {
            if ((time * 1000L) >= previousTime && (time * 1000L) <= currentTime) {
                flag = true;
            }
        } else if (String.valueOf(time).length() == 13) {
            if (time >= previousTime && time <= currentTime) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 将Long型时间戳（调用isLegal返回为true的合法时间戳）转换为Date
     *
     * @param time
     * @return
     */
    public static Date convertDate(Long time) {

        Calendar calendar = Calendar.getInstance();

        //将秒转换为毫秒
        if (String.valueOf(time).length() == 10) {
            time = time * 1000L;
        }

        calendar.setTimeInMillis(time);

        return calendar.getTime();
    }

    public static void main(String[] args) {

        System.out.println(isLegal(1514805297L));
        System.out.println(isLegal(1514805300000L));
        System.out.println(isLegal(1514805297L));
        System.out.println(isLegal(1514892900000L));

        System.out.println(convertDate(1514805297L));
        System.out.println(convertDate(1514805300000L));
    }

}
