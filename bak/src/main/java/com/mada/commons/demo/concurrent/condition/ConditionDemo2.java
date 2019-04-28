package com.mada.commons.demo.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现生产者/消费者模式：一对一交替打印
 *
 * @Auther: madali
 * @Date: 2018/7/2 20:08
 */
public class ConditionDemo2 {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private boolean hasValue = false;

    public void set() {
        try {
            lock.lock();
            while (hasValue) {
                condition.await();
            }
            System.out.println("打印set");
            hasValue = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void get() {
        try {
            lock.lock();
            while (!hasValue) {
                condition.await();
            }
            System.out.println("打印get");
            hasValue = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ConditionDemo2 demo = new ConditionDemo2();

        new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                demo.set();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                demo.get();
            }
        }).start();
        
    }
}
