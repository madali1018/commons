package com.common.demo.java8.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: madali
 * @Date: 2018/6/27 10:22
 */
public class CountDownLatchDemo2 {

    public static void main(String[] args) {

        int loopCount = 10000;
        int threadCount = 10;

        final SafeSeqWithLock lock = new SafeSeqWithLock();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;

            new Thread(() -> {
                for (int j = 0; j < loopCount; j++) {
                    lock.inc();
                }

                System.out.println("Finished:" + index);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("both have finished....");
        System.out.println("SafeSeqWithLock:" + lock.getCount());
    }

}
