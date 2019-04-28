package com.mada.commons.demo.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by madali on 2018/12/17 16:53
 */
public class TestCountDownLatch1 {

    public static void main(String[] args) {
        CountDownLatch countDown = new CountDownLatch(1);
        CountDownLatch await = new CountDownLatch(5);

        // 依次创建并启动处于等待状态的5个MyTask线程
        for (int i = 0; i < 5; ++i) {
            new Thread(new MyTask(countDown, await, i)).start();
        }

        System.out.println("用于触发处于等待状态的线程开始工作......");
        System.out.println("用于触发处于等待状态的线程工作完成，等待状态线程开始工作......");
        countDown.countDown();
        try {
            await.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Bingo!");
    }

    private static class MyTask implements Runnable {
        private final CountDownLatch countDown;
        private final CountDownLatch await;
        private final int i;

        public MyTask(CountDownLatch countDown, CountDownLatch await, int i) {
            this.countDown = countDown;
            this.await = await;
            this.i = i;
        }

        public void run() {
            try {
                countDown.await();//等待主线程执行完毕，获得开始执行信号...
                System.out.println("处于等待的线程开始自己预期工作......当前线程name:" + Thread.currentThread().getName() + ",i:" + i);
                await.countDown();//完成预期工作，发出完成信号...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
