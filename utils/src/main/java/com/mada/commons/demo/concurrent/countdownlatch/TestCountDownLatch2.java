package com.mada.commons.demo.concurrent.countdownlatch;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/17 16:59
 */
public class TestCountDownLatch2 {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            threadPool.execute(new MyTask(latch, i));
        }

        latch.await();
        System.err.println("等待线程被唤醒！");
        threadPool.shutdown();
    }

    private static class MyTask implements Runnable {
        CountDownLatch latch;
        int i;

        public MyTask(CountDownLatch latch, int i) {
            this.latch = latch;
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("当前线程name:" + Thread.currentThread().getName() + ",i:" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }

}
