package com.mada.commons.demo.concurrent.timer.p3;

import java.util.Timer;

/**
 * Created by madali on 2018/11/21 15:17
 * <p>
 * 在延迟指定时间后以指定的间隔时间循环执行定时任务
 */
public class Demo3 {

    Timer timer;

    // 程序延迟3秒执行，MyTask3执行的时间间隔为5秒
    public Demo3() {
        timer = new Timer();
        timer.schedule(new MyTask3(), 3000, 5000);
    }

    public static void main(String[] args) {
        new Demo3();
    }

}
