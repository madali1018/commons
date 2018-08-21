package com.common.demo.java8.thread;

/**
 * Created by madali on 2018/3/29.
 */
public class Runner implements Runnable{
    @Override
    public void run() {
        System.out.println("使用Runnable接口创建线程.");
    }
}
