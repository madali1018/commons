package com.mada.commons.demo.threadpool.p1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/4 17:34
 */
public class Test1 {

    public static void main(String[] args) {
        /*
         * 线程池的主要处理流程：
         * 1.任务进来时，首先判断核心线程池是否已经满了，未满则核心线程就先执行任务。
         * 2.如果核心线程池满了，就判断任务缓存队列是否有地方存放该任务，如果有就存放队列，等待核心线程池中的线程执行完其他任务再执行缓存队列中的任务。
         *  2.1 corePoolSize是正常情况下，线程池中的可用线程数，即 核心线程数。
         *  2.2 maximumPoolSize是请求突然骤增时，线程池中可以使用的最大的线程数目。
         *  2.3 核心线程数已满，新进请求是先判断任务缓存队列是否已满的。而不是直接判断maximumPoolSize是否已达到最大值。
         *  2.4 keepAliveTime是对非核心线程池生效的，即非核心线程池执行完任务后，空闲时间超过该值后是否需要关闭。
         *      核心线程池则不受该值影响。
         *
         * 3.如果队列满了，再判断最大可容纳的线程数，没有超过这个数量，就开启非核心线程执行任务，如果超出了就调用handler实现拒绝策略。
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());
        // allowCoreThreadTimeOut默认是false，即核心线程不会超时关闭，除非手动关闭线程池才会销毁线程。
        System.out.println("核心线程池超时是否关闭:" + executor.allowsCoreThreadTimeOut());
//        executor.allowCoreThreadTimeOut(true);

        /*
         * 1.线程池容量的动态调整：corePoolSize，maximumPoolSize，keepAliveTime
         * 2.参数由小变大时，ThreadPoolExecutor进行线程赋值，还可能立即创建新的线程来执行任务。
         */
        executor.setCorePoolSize(6);
        executor.setMaximumPoolSize(12);
        executor.setKeepAliveTime(15, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 20; i++) {
            executor.execute(new MyTask(i));
            System.out.println("当前线程池中线程数目:" + executor.getPoolSize()
                    + ",队列中等待执行的任务数目:" + executor.getQueue().size()
                    + ",已执行完的任务数目:" + executor.getCompletedTaskCount());// 已完成的线程数粗略值，非精准值
        }
        executor.shutdown();// 不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务。
//        executor.shutdownNow();// 立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务。

        if (executor.isTerminated()) {
            System.out.println("当前线程池中线程数目:" + executor.getPoolSize()
                    + ",队列中等待执行的任务数目:" + executor.getQueue().size()
                    + ",已执行完的任务数目:" + executor.getCompletedTaskCount());// 已完成的线程数粗略值，非精准值
        }
    }
}

