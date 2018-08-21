package com.common.demo.java8.concurrent.timer;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;

/**
 * @Auther: madali
 * @Date: 2018/7/10 20:00
 */
public class TimerDemo {

    @Test
    public void test1() {
        Timer timer = new Timer();
        MyTask task = new MyTask();

        timer.schedule(task, new Date());
    }

    @Test
    public void test2() {

        Timer timer = new Timer(true);
        MyTask task = new MyTask();

        // 程序运行结束后迅速结束当前进程，并且TimeTask中的任务不再执行，因为进程已经结束了。
        timer.schedule(task, new Date());
    }

}
