package com.mada.commons.demo.threadpool.p1;

/**
 * Created by madali on 2018/12/4 18:18
 */
public class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        System.out.println("当前线程名称:" + Thread.currentThread().getName() + ",正在执行task:" + taskNum);
        try {
            Thread.sleep(2 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task" + taskNum + "执行完毕.");
    }
}
