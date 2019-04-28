package com.mada.commons.demo.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * concurrent包提供了一个可以替代synchronized关键字的ReentrantLock，
 * 简单的说你可以new一个ReentrantLock， 然后通过lock.lock和lock.unlock来获取锁和释放锁；注意必须将unlock放在finally块里面，
 * ReentrantLock的好处
 * 1. 是更好的性能，
 * 2. 提供同一个lock对象上不同condition的信号通知
 * 3. 还提供lockInterruptibly这样支持响应中断的加锁过程，意思是说你试图去加锁，但是当前锁被其他线程hold住，然后你这个线程可以被中断；
 *
 * @Auther: madali
 * @Date: 2018/7/2 20:21
 */
public class ReentrantLockDemo {

    private ReentrantLock lock;

    public ReentrantLockDemo() {
        super();
        // ReentrantLock默认使用的是非公平锁
        lock = new ReentrantLock();
    }

    public ReentrantLockDemo(boolean fair) {
        super();
        lock = new ReentrantLock(fair);
    }

    public void serviceMethod() {
        try {
            lock.lock();
            System.out.println("ThreadName:" + Thread.currentThread().getName() + "获得锁");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        //公平锁
        ReentrantLockDemo demo = new ReentrantLockDemo(true);

        //非公平锁：ReentrantLock默认是非公平锁
//        ReentrantLockDemo demo = new ReentrantLockDemo();
//        ReentrantLockDemo demo = new ReentrantLockDemo(false);

        //多线程乱序执行，但公平锁情况下 线程获取锁和线程运行了的顺序是不变的。非公平锁情况下，线程获取锁和线程运行了的顺序是乱序的。
        Thread[] arr = new Thread[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = new Thread(() -> {
                demo.serviceMethod();
                System.out.println("ThreadName:" + Thread.currentThread().getName() + "运行了");
            });
        }

        for (int i = 0; i < 10; i++) {
            arr[i].start();
        }

        System.out.println("当前使用的ReentrantLock锁模式是否是公平锁:" + demo.lock.isFair());
    }

}


