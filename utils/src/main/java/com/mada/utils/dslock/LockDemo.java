package com.mada.utils.dslock;

import com.mada.utils.dslock.contract.DsLock;
import com.mada.utils.dslock.service.DsLockFactory;
import com.mada.utils.dslock.service.RedisLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2019/10/24 15:03
 */
public class LockDemo {

    private static RedissonClient redissonClient = DsLockFactory.createRedisson("", "", "");

    private static final int LEASE_TIME = 500;
    private static final int WAIT_TIME = 1000;
    private static final String PREFIX_LOCAL_KEY = "test-lock-key-";

    public static boolean lock(String key) throws InterruptedException {
        DsLock lock = new RedisLock(PREFIX_LOCAL_KEY + key, LEASE_TIME, redissonClient);
        return lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    public static void unlock(String key) {
        DsLock lock = new RedisLock(PREFIX_LOCAL_KEY + key, LEASE_TIME, redissonClient);
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(redissonClient);

        int count = 0;

        for (int i = 0; i < 10; i++) {
            count++;
            String s = count + "sr5qwtre";

            long t1 = System.currentTimeMillis();
            lock(s);
            long t2 = System.currentTimeMillis();

            Thread.sleep(20);

            long t3 = System.currentTimeMillis();
            unlock(s);
            long t4 = System.currentTimeMillis();

            System.out.println((t2 - t1) + "," + (t3 - t2) + "," + (t4 - t3) + "\t");
        }
    }

}
