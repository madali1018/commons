package com.common.demo.java8.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Function: 两个线程交替执行打印 1~100
 * <p>
 * lock版
 *
 * @Auther: madali
 * @Date: 2018/8/28 16:19
 */
public class TwoThread {

    private int start = 1;
    private boolean flag = false;

    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {

        TwoThread twoThread = new TwoThread();

        Thread thread1 = new Thread(new JiNumber(twoThread));
        thread1.setName("thread1");
        thread1.start();

        Thread thread2 = new Thread(new OuNumber(twoThread));
        thread2.setName("thread2");
        thread2.start();
    }

    private static class OuNumber implements Runnable {

        private TwoThread number;

        public OuNumber(TwoThread twoThread) {
            this.number = twoThread;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                if (number.flag) {
                    try {
                        reentrantLock.lock();
                        System.out.println(Thread.currentThread().getName() + ":" + number.start);
                        number.start++;
                        number.flag = false;
                    } finally {
                        reentrantLock.unlock();
                    }
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static class JiNumber implements Runnable {

        private TwoThread number;

        public JiNumber(TwoThread twoThread) {
            this.number = twoThread;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                if (!number.flag) {
                    try {
                        reentrantLock.lock();
                        System.out.println(Thread.currentThread().getName() + ":" + number.start);
                        number.start++;
                        number.flag = true;
                    } finally {
                        reentrantLock.unlock();
                    }
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
