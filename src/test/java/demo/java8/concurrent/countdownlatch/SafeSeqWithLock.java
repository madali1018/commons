package demo.java8.concurrent.countdownlatch;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: madali
 * @Date: 2018/8/28 16:18
 */
public class SafeSeqWithLock {

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
