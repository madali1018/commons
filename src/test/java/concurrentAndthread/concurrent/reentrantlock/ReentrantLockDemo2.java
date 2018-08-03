package concurrentAndthread.concurrent.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: madali
 * @Date: 2018/7/2 20:21
 */
public class ReentrantLockDemo2 {

    private ReentrantLock lock;

    public ReentrantLockDemo2() {
        super();
        // ReentrantLock默认使用的是非公平锁
        lock = new ReentrantLock();
    }

    public ReentrantLockDemo2(boolean fair) {
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
//        ReentrantLockDemo2 demo = new ReentrantLockDemo2();

        //非公平锁
        ReentrantLockDemo2 demo = new ReentrantLockDemo2(false);

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
