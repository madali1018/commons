package com.mada.commons.demo.concurrent.atomic;

import java.util.concurrent.CountDownLatch;

/**
 * Atomic相关的类， 比如AtomicLong, AtomicInteger等这些，都是线程安全且支持无阻塞无锁定的。
 *
 * @Auther: madali
 * @Date: 2018/6/27 09:53
 */
public class AtomicLongTest {

    public static void main(String[] args) {

        int threadCount = 10;
        int loopCount = 10000;

        final UnSafeSeq unSafeSeq = new UnSafeSeq();
        final SafeSeq safeSeq = new SafeSeq();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            new Thread(() -> {
                for (int j = 0; j < loopCount; j++) {
                    /*
                     * 10个线程，每个线程运行了10000次，理论上应该有100000次增加，使用了普通的long是非线程安全的，而使用了AtomicLong是线程安全的；
                     *
                     * 注意，这个例子也说明，虽然long本身的单个设置是原子的，要么成功要么不成功，但是诸如count++ 这样的操作就不是线程安全的；因为这包括了读取和写入两步操作。
                     */
                    unSafeSeq.inc();
                    safeSeq.inc();
                }
                System.out.println("Finished: " + index);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Both have finished...");
        System.out.println("UnSafeSeq:" + unSafeSeq.get());
        System.out.println("SageSeq:" + safeSeq.get());
    }

}

