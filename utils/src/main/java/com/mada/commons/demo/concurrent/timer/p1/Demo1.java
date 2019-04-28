package com.mada.commons.demo.concurrent.timer.p1;

import java.util.Timer;

/**
 * @Auther: madali
 * @Date: 2018/7/10 20:00
 * <p>
 * 指定延迟时间执行定时任务
 */
public class Demo1 {

    Timer timer;

    public Demo1(int time) {
        timer = new Timer();
        timer.schedule(new MyTask(), time * 1000L);
    }

    public static void main(String[] args) {
        System.out.println("timer begin");
        new Demo1(3);
    }

}
