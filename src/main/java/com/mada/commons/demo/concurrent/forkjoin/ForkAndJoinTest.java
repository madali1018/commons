package com.mada.commons.demo.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join框架demo
 * <p>
 * RecursiveTask：用于有返回值的任务。
 *
 * @Auther: madali
 * @Date: 2018/8/31 16:34
 */
public class ForkAndJoinTest extends RecursiveTask<Integer> {

    // 设立一个最大计算容量
    private static final int DEFAULT_CAPACITY = 100;

    // 用2个数字表示目前要计算的范围
    private int start;
    private int end;

    public ForkAndJoinTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        // 如果任务量在最大容量之内：计算任务
        boolean canCompute = (end - start) <= DEFAULT_CAPACITY;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 计算容量中间值：如果超过了最大容量，那么就进行拆分处理
            int middle = (start + end) / 2;
            // 递归
            ForkAndJoinTest leftTask = new ForkAndJoinTest(start, middle);
            ForkAndJoinTest rightTask = new ForkAndJoinTest(middle + 1, end);

            // 执行任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行并返回结果
            sum = leftTask.join() + rightTask.join();
        }

        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // ForkJoinPool线程池：提交任务和调度任务
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 任务执行类(ForkJoinTask的子类)：生成一个计算任务，计算1+2+3+...+10000
        ForkAndJoinTest task = new ForkAndJoinTest(1, 10000);

        long result = forkJoinPool.invoke(task);
        System.out.println(result);

        long result2 = forkJoinPool.submit(task).get();
        System.out.println(result2);
    }

}