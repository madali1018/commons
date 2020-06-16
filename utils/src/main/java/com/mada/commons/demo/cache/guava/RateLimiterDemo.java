package com.mada.commons.demo.cache.guava;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2019/4/1 11:57
 */
@Log4j2
public class RateLimiterDemo {

    // 限制频率：创建一个限流器，参数代表每秒生成的令牌数，即每秒只发出多少个令牌
    private static double permitsPerSecond = 5D;

    private static RateLimiter limiter = RateLimiter.create(permitsPerSecond);

    @Test
    public void t1() {
        for (int i = 1; i < 20; i++) {
            // 以阻塞的方式获取令牌，当然也可以通过tryAcquire(int permits, long timeout, TimeUnit unit)来设置等待超时时间的方式获取令牌，如果超timeout为0，则代表非阻塞，获取不到立即返回。
            // acquire 的返回表示的是等待时间
            double waitTime = limiter.acquire(i);
            System.out.println("now:" + OffsetDateTime.now() + ",acq:" + i + ",waitTime:" + waitTime);
        }
    }

    @Test
    public void t2() {
        for (int i = 0; i < 20; i++) {
            if (limiter.tryAcquire()) {
                System.out.println("成功,i:" + i);
            } else {
                System.out.println("失败,i:" + i);
            }
        }
    }

    public static void main(String[] args) {
        try {
            access();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void access() throws Exception {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            final int num = i;

            fixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    if (limiter.tryAcquire()) {
                        System.out.println("成功,num:" + num);
                    } else {
                        System.out.println("失败,num:" + num);
                    }
                }
            });
        }

        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

}
