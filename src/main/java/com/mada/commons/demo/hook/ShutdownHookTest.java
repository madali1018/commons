package com.mada.commons.demo.hook;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by madali on 2019/2/21 19:00
 */
public class ShutdownHookTest {

    private static Timer timer = new Timer("job-timer");

    private static AtomicInteger count = new AtomicInteger(0);

    private static class CleanWorkThread extends Thread {
        @Override
        public void run() {
            System.out.println("clean some work.");
            timer.cancel();
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //将hook线程添加到运行时环境中去
        Runtime.getRuntime().addShutdownHook(new CleanWorkThread());

        System.out.println("main class start ..... ");
        //简单模拟
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count.getAndIncrement();
                System.out.println("doing job " + count);
                if (count.get() == 3) {
                    System.exit(0);
                }
            }
        }, 0, 2 * 1000);
    }

}
