package com.mada.utils.dslock.service;

import com.mada.utils.dslock.contract.DsLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2019/10/24 14:17
 */
public class RedisLock implements DsLock, AutoCloseable {

    private RedissonClient redissonClient;
    String lockKey;
    RLock rLock;
    long leaseTime;

    RedisLock() {
    }

    /**
     * @param lockKey        key
     * @param leaseTime      锁的持续时间, 毫秒时间戳
     * @param redissonClient redis client
     */
    public RedisLock(String lockKey, long leaseTime, RedissonClient redissonClient) {
        this.lockKey = lockKey;
        this.leaseTime = leaseTime;
        this.redissonClient = redissonClient;
    }

    private RLock getLock() {
        if (rLock == null) {
            synchronized (this) {
                if (rLock == null) {
                    rLock = redissonClient.getLock(lockKey);
                }
            }
        }
        return rLock;
    }

    /**
     * 阻塞式获取锁，无法被打断
     */
    @Override
    public void lock() {
        getLock().lock(leaseTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 阻塞式获取锁，可以被打断
     *
     * @throws InterruptedException exp
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        getLock().lockInterruptibly(leaseTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 非阻塞式，立刻返回结果
     *
     * @return 是否成功
     */
    @Override
    public boolean tryLock() {
        try {
            return tryLock(-1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 最大等待time的时间
     *
     * @return 是否成功
     */
    @Override
    public boolean tryLock(long time, TimeUnit timeUnit) throws InterruptedException {
        return getLock().tryLock(time, leaseTime, timeUnit);
    }

    @Override
    public void unlock() {
        getLock().forceUnlock();
    }

    @Override
    public boolean isOwner() {
        return getLock().isHeldByCurrentThread();
    }

    @Override
    public void close() {
        unlock();
    }

}
