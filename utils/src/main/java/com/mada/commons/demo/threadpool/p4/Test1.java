package com.mada.commons.demo.threadpool.p4;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2019/4/8 15:22
 */
public class Test1 {

    private static int i = 0;

    public static void main(String[] args) {
        t1();
    }

    private static void t1() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // scheduleAtFixedRate 是以上一个任务开始的时间计时，10秒过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                i++;
                if (i == 4) {
                    throw new RuntimeException();
                } else {
                    System.out.println(OffsetDateTime.now() + "," + i);
                }

                if (i == 2) {
                    try {
                        Thread.sleep(10 * 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RuntimeException ex) {
                System.out.println("在ScheduledExecutorService中有异常抛出，异常堆栈：" + Arrays.toString(ex.getStackTrace()));
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private static void t2() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // scheduleWithFixedDelay 是以上一个任务结束时开始计时，10秒过去后，立即执行。
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                i++;
                if (i == 6) {
                    throw new RuntimeException();
                } else {
                    System.out.println(OffsetDateTime.now() + "," + i);
                }
            } catch (Exception ex) {
                System.out.println("在ScheduledExecutorService中有异常抛出，异常堆栈：" + Arrays.toString(ex.getStackTrace()));
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private static void t3() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture future = service.scheduleAtFixedRate(() -> {
            i++;
            if (i == 6) {
                throw new RuntimeException();
            } else {
                System.out.print(i + " ");
            }
        }, 0, 1, TimeUnit.SECONDS);

        try {
            future.get();
        } catch (Exception e) {
            System.out.println("在ScheduledExecutorService中有异常抛出，异常堆栈：" + Arrays.toString(e.getStackTrace()));
        }
    }

    private static void t4() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("now:"+ OffsetDateTime.now()), 0, 2, TimeUnit.SECONDS);
    }

}
