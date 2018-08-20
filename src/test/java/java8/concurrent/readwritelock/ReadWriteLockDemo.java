package java8.concurrent.readwritelock;

import java.time.OffsetDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读锁可以有很多个锁同时上锁，只要当前没有写锁。
 * <p>
 * 写锁是排他的，上了写锁，其他线程既不能上读锁，也不能上写锁；同样需要上写锁的前提是既没有读锁，也没有写锁。
 * 两个写锁不能同时获得无需说明。
 *
 * @Auther: madali
 * @Date: 2018/6/27 12:32
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        final Lock readLock = lock.readLock();
        final Lock writeLock = lock.writeLock();

        final CountDownLatch latch = new CountDownLatch(2);

        // 开启读线程
        new Thread(() -> {

            System.out.println(OffsetDateTime.now() + " now to get readLock.");
            readLock.lock();

            try {
                Thread.currentThread().sleep(2 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(OffsetDateTime.now() + " now to unlock readLock.");
            readLock.unlock();

            latch.countDown();
        }).start();

        // 开启写线程
        // 试图获得写锁的线程，只有当另外一个线程将读锁释放了以后才可以获得。
        new Thread(() -> {

            System.out.println(OffsetDateTime.now() + " now to get writeLock.");
            writeLock.lock();

            System.out.println(OffsetDateTime.now() + " now to unlock writeLock.");
            writeLock.unlock();

            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(OffsetDateTime.now() + " finished.");

    }

}
