package com.mada.commons.demo.threadpool.p2;

/**
 * Created by madali on 2018/12/8 15:21
 */
public class MyTask implements Runnable {

    private int taskNum;

    public MyTask(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        System.out.println("当前线程" + Thread.currentThread().getName() + "正在执行task" + taskNum);
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程" + Thread.currentThread().getName() + "执行task" + taskNum + "完毕.");
    }
}
