package com.mada.commons.demo.shceduled.p3;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by madali on 2018/12/5 10:39
 */
public class Test1 {

    public static void main(String[] args) {
        test();
    }

    // 使用Callable延迟运行
    private static void test() {
        try {
            List<Callable> callableList = new ArrayList<>();
            callableList.add(new MyCallableA());
            callableList.add(new MyCallableB());

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            // 延时2秒执行
            ScheduledFuture futureA = executorService.schedule(callableList.get(0), 2, TimeUnit.SECONDS);
            // 延时10秒执行
            ScheduledFuture futureB = executorService.schedule(callableList.get(1), 10, TimeUnit.SECONDS);

            System.out.println("x:" + OffsetDateTime.now());
            System.out.println("返回值A:" + futureA.get());
            System.out.println("返回值B:" + futureB.get());
            System.out.println("y:" + OffsetDateTime.now());

            executorService.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
