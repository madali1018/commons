package com.common.demo.java8.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by madali on 2017/12/27.
 */
public class ThreadDemo2 {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> System.out.println(123));
        thread.start();

        //线程挂起
//        ThreadUtil.suspend(thread);

        //线程恢复
//        ThreadUtil.resume(thread);

        //固定线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 500; i++) {
            int index = i;
            fixedThreadPool.execute(() -> {
                System.out.println("Current thread index number is:" + (index + 1));
            });
        }

    }

}
