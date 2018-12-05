package com.mada.commons.demo.shceduled.p1;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2018/12/5 10:30
 */
public class Test1 {

    public static void main(String[] args) {
        test1();
    }

    // scheduleAtFixedRate()方法实现周期性执行：立刻执行，而且每隔2秒执行一次。
    private static void test1() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> System.out.println("Now:" + OffsetDateTime.now()),
                0, 2, TimeUnit.SECONDS);
    }
}
