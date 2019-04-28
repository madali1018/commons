package com.mada.commons.demo.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * fork/join
 * <p>
 * RecursiveTask：用于有返回值的任务
 * <p>
 * 1.Java8 Stream 的实现原理本质上就是在ForkJoin上进行了一层封装，将Stream 不断尝试分解成更小的split，然后使用fork/join 框架分而治之
 *
 * @Auther: madali
 * @Date: 2018/8/31 16:34
 */
public class ForkAndJoinTest extends RecursiveTask<Integer> {

    // 设立一个最大计算容量
    private static final int MAX = 100;

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
        boolean canCompute = (end - start) <= MAX;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            System.out.println("thread:" + Thread.currentThread().getName() + ", range:[" + start + ", " + end + "]");
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

        //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);

        // 关闭线程池
        forkJoinPool.shutdown();

        long result = forkJoinPool.invoke(task);
        System.out.println(result);

        long result2 = forkJoinPool.submit(task).get();
        System.out.println(result2);
    }

}