package com.mada.commons.demo.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * RecursiveAction：用于没有返回值的任务。
 * <p>
 * Created by madali on 2019/4/17 16:31
 */
public class ForkJoinTest2 extends RecursiveAction {

    private static final int MAX = 20;

    private int start;
    private int end;

    public ForkJoinTest2(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        //当end-start的值小于MAX时，开始打印
        if ((end - start) < MAX) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "i的值" + i);
            }
        } else {
            System.out.println("thread:" + Thread.currentThread().getName() + ", range:[" + start + ", " + end + "]");
            // 将大任务分解成两个小任务
            int middle = (start + end) / 2;
            ForkJoinTest2 left = new ForkJoinTest2(start, middle);
            ForkJoinTest2 right = new ForkJoinTest2(middle, end);
            left.fork();
            right.fork();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 提交可分解的PrintTask任务
        forkJoinPool.submit(new ForkJoinTest2(0, 1000));

        //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);

        // 关闭线程池
        forkJoinPool.shutdown();
    }

}
