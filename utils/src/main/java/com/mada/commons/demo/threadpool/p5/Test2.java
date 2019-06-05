package com.mada.commons.demo.threadpool.p5;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by madali on 2019/6/5 11:15
 */
public class Test2 {

    public static void main(String[] args) {
        // 总的统计
        CountEntity allCountEntity = CountEntity.builder()
                .num1(new LongAdder())
                .num2(new LongAdder())
                .build();

        for (int i = 0; i < 10; i++) {
            // 每一个线程池的统计
            CountEntity countEntity = CountEntity.builder()
                    .num1(new LongAdder())
                    .num2(new LongAdder())
                    .build();

            ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
            executor.execute(new TestTask(countEntity, allCountEntity));

            // 等待线程池中线程都执行完毕后再向下执行
            executor.shutdown();
            while (true) {
                try {
                    // 每5s执行一次判断操作：线程池中线程是否已完全执行完
                    if (executor.awaitTermination(5, TimeUnit.SECONDS)) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("线程池" + i + "的统计结果,num1:" + countEntity.num1.longValue() + ",num2:" + countEntity.num2.longValue());
        }

        System.out.println("总的统计结果,num1:" + allCountEntity.num1.longValue() + ",num2:" + allCountEntity.num2.longValue());
    }

    private static class TestTask implements Runnable {
        private CountEntity countEntity;
        private CountEntity allCountEntity;

        public TestTask(CountEntity countEntity, CountEntity allCountEntity) {
            this.countEntity = countEntity;
            this.allCountEntity = allCountEntity;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                if (i % 2 == 0) {
                    countEntity.num1.increment();
                    allCountEntity.num1.increment();
                } else {
                    countEntity.num2.increment();
                    allCountEntity.num2.increment();
                }
            }
        }
    }

    @Data
    @Builder
    private static class CountEntity {
        public LongAdder num1;
        public LongAdder num2;
    }

}
