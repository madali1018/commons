package com.mada.commons.demo.shceduled.p3;

import java.time.OffsetDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/5 10:39
 */
public class MyCallableB implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("callB begin:" + Thread.currentThread().getName() + ", " + OffsetDateTime.now());// 因为futureB延迟10秒执行，故此处时间和 x: 的时间差为10秒
        //线程休眠1秒再执行
        TimeUnit.SECONDS.sleep(1);
        System.out.println("callB end:  " + Thread.currentThread().getName() + ", " + OffsetDateTime.now());
        return "B";
    }
}