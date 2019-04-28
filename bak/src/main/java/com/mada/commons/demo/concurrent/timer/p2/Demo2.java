package com.mada.commons.demo.concurrent.timer.p2;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by madali on 2018/11/21 15:11
 * <p>
 * 在指定时间执行定时任务
 */
public class Demo2 {

    Timer timer;

    public Demo2() {
        Date time = getTime();
        System.out.println("指定时间time=" + time);
        timer = new Timer();
        timer.schedule(new MyTask(), time);
    }

    public Date getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 00);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        new Demo2();
    }

}
