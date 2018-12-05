package com.mada.commons.demo.shceduled.p2;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/5 10:34
 */
public class Test1 {

    public static void main(String[] args) {
        test1();
    }

    // scheduleWithFixedDelay()方法实现周期性执行
    private static void test1() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("x = " + OffsetDateTime.now());
        //延时1秒执行，每隔2秒执行一次
        executorService.scheduleWithFixedDelay(new MyTask(), 1, 2, TimeUnit.SECONDS);
        System.out.println("y = " + OffsetDateTime.now());
    }

}


