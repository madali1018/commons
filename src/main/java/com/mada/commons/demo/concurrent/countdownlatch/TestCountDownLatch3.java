package com.mada.commons.demo.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：用来在一个线程中等待多个线程完成任务。
 *
 * @Auther: madali
 * @Date: 2018/6/27 09:42
 */
public class TestCountDownLatch3 {

    public static void main(String[] args) {
        int count = 10;
        // 前10个线程执行完成顺序会变化(多线程乱序执行)，但最后一句始终会等待前面10个线程都执行完后才会执行。
        final CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程:" + Thread.currentThread().getName() + "执行完.");
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(count + "个线程已经执行完");
    }

}
