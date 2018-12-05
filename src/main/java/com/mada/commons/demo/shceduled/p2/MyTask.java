package com.mada.commons.demo.shceduled.p2;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/5 10:36
 */
public class MyTask implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("begin:" + OffsetDateTime.now() + ", name:" + Thread.currentThread().getName());
            //线程休眠3秒再执行
            TimeUnit.SECONDS.sleep(3);
            System.out.println("end:" + OffsetDateTime.now() + ", name:" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
