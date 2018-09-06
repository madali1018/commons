package com.mada.common.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: madali
 * @Date: 2018/9/6 16:23
 */
public class LogDemo2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDemo2.class);

    /**
     * logback支持异步记录日志，这样可加快程序的主流程处理速度，提高接口的qps。
     * logback异步记录日志的原理，也是使用一个缓冲队列，当缓冲数量到一定阀值时，才把日志写到文件里。
     * 需要在logback.xml中配置异步输出appender
     */
    public static void main(String[] args) {
        int messageSize = 500000;
        int threadSize = 50;
        final int everySize = messageSize / threadSize;

        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        long startTime = System.currentTimeMillis();
        for (int ts = 0; ts < threadSize; ts++) {
            new Thread(() -> {
                for (int es = 0; es < everySize; es++) {
                    LOGGER.info(Thread.currentThread().getName() + "...es:{}.", es);
                }
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("logback:messageSize = " + messageSize + ",threadSize = " + threadSize + ",costTime = " + (endTime - startTime) + "ms");
    }

}
