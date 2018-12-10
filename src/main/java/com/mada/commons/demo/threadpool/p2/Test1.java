package com.mada.commons.demo.threadpool.p2;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/8 15:20
 */
public class Test1 {

    /**
     * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
     * <p>
     * 1. ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * 2. ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * 3. ThreadPoolExecutor.DiscardOldestPolicy：不丢弃任务，而是丢弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     * 4. ThreadPoolExecutor.CallerRunsPolicy：重试添加当前的任务，会自动重复调用execute方法，直到成功。
     */
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 100; i++) {
            executor.execute(new MyTask(i));
        }

        executor.shutdown();
    }

}
