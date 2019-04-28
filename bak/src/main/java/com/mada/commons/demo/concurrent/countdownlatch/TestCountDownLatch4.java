package com.mada.commons.demo.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: madali
 * @Date: 2018/6/27 10:22
 */
public class TestCountDownLatch4 {

    public static void main(String[] args) {
        int loopCount = 10000;
        int threadCount = 10;

        final SafeSeqWithLock lock = new SafeSeqWithLock();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < loopCount; j++) {
                    lock.inc();
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

        System.out.println("both have finished....");
        System.out.println("线程安全锁执行完后的SafeSeqWithLock:" + lock.getCount());
    }

}
