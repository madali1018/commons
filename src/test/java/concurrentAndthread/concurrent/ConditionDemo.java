package concurrentAndthread.concurrent;

import java.time.OffsetDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: madali
 * @Date: 2018/6/27 14:31
 */
public class ConditionDemo {

    /*
     * 对于同一个锁对象可以创建多个Condition(对象监听器)。
     *
     * 例如，假如多线程读/写同一个缓冲区：当向缓冲区中写入数据之后，唤醒"读线程"；当从缓冲区读出数据之后，唤醒"写线程"；并且当缓冲区满的时候，"写线程"需要等待；当缓冲区为空时，"读线程"需要等待。
     * 如果采用Object类中的wait(), notify(), notifyAll()实现该缓冲区，当向缓冲区写入数据之后需要唤醒"读线程"时，不可能通过notify()或notifyAll()明确的指定唤醒"读线程"，
     * 而只能通过notifyAll唤醒所有线程(但是notifyAll无法区分唤醒的线程是读线程，还是写线程)。
     * 但是，通过Condition，就能明确的指定唤醒读线程。
     *
     * synchronized相当于在整个lock对象上只有一个单一的Condition对象，所有的线程都注册在它一个对象的身上。
     *
     */

    Lock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    int bufferSize = 5;
    Object[] items = new Object[bufferSize];

    int putPtr;
    int takePtr;
    int count;

    public static void main(String[] args) {

        testPutTake();

//        testTakePut();
    }

    // 先put再take
    private static void testPutTake() {
        final ConditionDemo buffer = new ConditionDemo();

        int count = 10;
        final CountDownLatch latch = new CountDownLatch(2 * count);

        System.out.println(OffsetDateTime.now() + " now try to call put for " + count);

        for (int i = 0; i < count; i++) {
            final int index = i;

            new Thread(() -> {
                try {
                    buffer.put(index);
                    System.out.println(OffsetDateTime.now() + " put finished: " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            }).start();
        }

        try {
            System.out.println(OffsetDateTime.now() + " main thread is going to sleep for 3 seconds");
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(OffsetDateTime.now() + " now try to take for count: " + count);

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    Object obj = buffer.take();
                    System.out.println(OffsetDateTime.now() + " take get:" + obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            }).start();
        }

        try {
            System.out.println(OffsetDateTime.now() + " main thread is to wait for all threads");
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(OffsetDateTime.now() + " all threads finished");
    }

    // 先take再put
    private static void testTakePut() {

        final ConditionDemo buffer = new ConditionDemo();

        int count = 10;
        final CountDownLatch latch = new CountDownLatch(2 * count);

        System.out.println(OffsetDateTime.now() + "," + " first try to call take for count: " + count);

        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread(() -> {

                Thread.currentThread().setName("TAKE_" + index);

                try {
                    Object obj = buffer.take();
                    System.out.println(OffsetDateTime.now() + ", take get:" + obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            }).start();
        }

        try {
            System.out.println(OffsetDateTime.now() + " main thread is going to sleep for 10 seconds");
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(OffsetDateTime.now() + " now try to call put for " + count);

        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread(() -> {
                Thread.currentThread().setName("PUT_" + index);

                try {
                    buffer.put(index);
                    System.out.println(OffsetDateTime.now() + ", put finished:" + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                latch.countDown();
            }).start();
        }

        try {
            System.out.println(OffsetDateTime.now() + ": main thread is to wait for all threads");
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(OffsetDateTime.now() + " all threads finished");
    }

    private void put(Object obj) throws InterruptedException {

        lock.lock();

        try {

            while (count == items.length) {
                System.out.println(OffsetDateTime.now() + ", " + Thread.currentThread().getName() + " put is to wait...");
                /*
                 * Condition的作用是对锁进行更精确的控制。Condition中的await()方法相当于Object的wait()方法，Condition中的signal()方法相当于Object的notify()方法，Condition中的signalAll()相当于Object的notifyAll()方法。
                 * 不同的是，Object中的wait(),notify(),notifyAll()方法是和"同步锁"(synchronized关键字)捆绑使用的；而Condition是需要与"互斥锁"/"共享锁"捆绑使用的。
                 */
                notFull.await();
            }

            items[putPtr] = obj;

            if (++putPtr == items.length) {
                putPtr = 0;
            }

            ++count;

            notEmpty.signal();

        } finally {
            lock.unlock();
        }

    }

    private Object take() throws InterruptedException {

        lock.lock();

        try {
            while (count == 0) {
                System.out.println(OffsetDateTime.now() + ", " + Thread.currentThread().getName() + " take is going to wait.");
                notEmpty.await();
            }

            Object obj = items[takePtr];

            if (++takePtr == items.length) {
                takePtr = 0;
            }

            --count;

            notFull.signal();

            return obj;
        } finally {
            lock.unlock();
        }

    }

}
