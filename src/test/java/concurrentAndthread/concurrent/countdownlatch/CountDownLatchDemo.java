package concurrentAndthread.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：用来在一个线程中等待多个线程完成任务。
 *
 * @Auther: madali
 * @Date: 2018/6/27 09:42
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {

        int count = 10;
        // 前10个线程执行完成顺序会变化(多线程乱序执行)，但最后一句始终会等待前面10个线程都执行完后才会执行。
        final CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread(() -> {
                try {
                    Thread.currentThread().sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread " + index + " has finished.");
                countDownLatch.countDown();
            }).start();

        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished.");
    }

}
