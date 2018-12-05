package com.mada.commons.demo.shceduled.p3;

import java.time.OffsetDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/5 10:39
 */
public class MyCallableA implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("callA begin:" + Thread.currentThread().getName() + ", " + OffsetDateTime.now());// 因为futureA延迟2秒执行，故此处时间和 x: 的时间差为2秒
        //线程休眠3秒再执行
        TimeUnit.SECONDS.sleep(3);
        System.out.println("callA end:  " + Thread.currentThread().getName() + ", " + OffsetDateTime.now());
        return "A";
    }
}