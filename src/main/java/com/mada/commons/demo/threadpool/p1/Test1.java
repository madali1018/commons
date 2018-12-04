package com.mada.commons.demo.threadpool.p1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/4 17:34
 */
public class Test1 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());
        // allowCoreThreadTimeOut默认是false，即核心线程不会超时关闭，除非手动关闭线程池才会销毁线程。
        System.out.println("核心线程池超时是否关闭:" + executor.allowsCoreThreadTimeOut());
//        executor.allowCoreThreadTimeOut(true);

        for (int i = 0; i < 20; i++) {
            executor.execute(new MyTask(i));
            System.out.println("当前线程池中线程数目:" + executor.getPoolSize()
                    + ",队列中等待执行的任务数目:" + executor.getQueue().size()
                    + ",已执行完的任务数目:" + executor.getCompletedTaskCount());// 已完成的线程数粗略值，非精准值
        }
        executor.shutdown();// 手动关闭线程池

        if (executor.isTerminated()) {
            System.out.println("当前线程池中线程数目:" + executor.getPoolSize()
                    + ",队列中等待执行的任务数目:" + executor.getQueue().size()
                    + ",已执行完的任务数目:" + executor.getCompletedTaskCount());// 已完成的线程数粗略值，非精准值
        }
    }
}

