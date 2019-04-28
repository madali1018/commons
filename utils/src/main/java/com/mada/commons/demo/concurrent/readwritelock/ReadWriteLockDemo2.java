package com.mada.commons.demo.concurrent.readwritelock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 在一个线程中，读锁不能直接升级为写锁，但是写锁可以降级为读锁。即：
 * 1：已经有了读锁，再去试图获取写锁，将会无法获得，一直堵住；
 * 2：已经有了写锁，再去试图获取读锁，没问题。
 *
 * @Auther: madali
 * @Date: 2018/6/27 14:17
 */
public class ReadWriteLockDemo2 {

    @Test
    // 降级（写锁 --> 读锁）：正常运行
    public void test1() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();

        System.out.println("now to get writeLock.");
        writeLock.lock();

        System.out.println("now to get readLock.");
        readLock.lock();

        System.out.println("now to unlock writeLock.");
        writeLock.unlock();

        System.out.println("now to unlock readLock.");
        readLock.unlock();

        System.out.println("finished.");
    }

    @Test
    // 升级（读锁 --> 写锁）：只能打印出前两句，后面就一直挂住了。
    public void test2() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();

        System.out.println("now to get readLock.");
        readLock.lock();

        System.out.println("now to get writeLock.");
        writeLock.lock();

        System.out.println("now to unlock writeLock.");
        writeLock.unlock();

        System.out.println("now to unlock readLock.");
        readLock.unlock();

        System.out.println("finished.");
    }

}


