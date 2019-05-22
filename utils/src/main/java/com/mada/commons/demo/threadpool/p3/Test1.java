package com.mada.commons.demo.threadpool.p3;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by madali on 2019/4/10 17:22
 */
@Log4j2
public class Test1 {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(500), new ThreadPoolExecutor.CallerRunsPolicy());

    private static CountEntity countEntity = CountEntity.builder().num1(new LongAdder()).num2(new LongAdder()).build();

    public static void main(String[] args) {

        for (int i = 0; i < 100000; i++) {
            threadPoolExecutor.execute(new MyTask(i, countEntity));
        }

        threadPoolExecutor.shutdown();

        // 等待线程池中线程都执行完毕后再向下执行
        while (true) {
            try {
                // 每10ms执行一次判断操作：线程池中线程是否已完全执行完
                if (threadPoolExecutor.awaitTermination(10, TimeUnit.MILLISECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("统计结果{}", countEntity);

    }

}
