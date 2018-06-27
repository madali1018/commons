package concurrentAndthread.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * concurrent包提供了一个可以替代synchronized关键字的ReentrantLock，
 * 简单的说你可以new一个ReentrantLock， 然后通过lock.lock和lock.unlock来获取锁和释放锁；注意必须将unlock放在finally块里面，
 * reentrantlock的好处
 * 1. 是更好的性能，
 * 2. 提供同一个lock对象上不同condition的信号通知
 * 3. 还提供lockInterruptibly这样支持响应中断的加锁过程，意思是说你试图去加锁，但是当前锁被其他线程hold住，然后你这个线程可以被中断；
 *
 * @Auther: madali
 * @Date: 2018/6/27 10:22
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {

        int loopCount = 100;
        int threadCount = 10;

        final SafeSeqWithLock lock = new SafeSeqWithLock();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;

            new Thread(() -> {
                for (int j = 0; j < loopCount; j++) {
                    lock.inc();
                }

                System.out.println("Finished:" + index);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("both have finished....");
        System.out.println("SafeSeqWithLock:" + lock.getCount());
    }

}

class SafeSeqWithLock {
    private long count = 0;

    private ReentrantLock reentrantLock = new ReentrantLock();

    public void inc() {
        reentrantLock.lock();

        try {
            // 此处其实无需加try finally块。只是为了遵循习惯写法：将unlock()写在finally中
            count++;
        } finally {
            reentrantLock.unlock();
        }

    }

    public long getCount() {
        return count;
    }
}