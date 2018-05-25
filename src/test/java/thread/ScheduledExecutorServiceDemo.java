package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 任务调度（定时任务+线程池，实现循环，延迟等功能）
 * @Auther: madali
 * @Date: 2018/5/25 11:24
 */
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) {

        test1();
//        test2();
//        test3();

    }

    //1.scheduleAtFixedRate()方法实现周期性执行：立刻执行，而且每隔1秒执行一次。
    private static void test1() {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("run:" + System.currentTimeMillis());
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    //2.scheduleWithFixedDelay()方法实现周期性执行
    private static void test2() {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("x = " + System.currentTimeMillis());
        //延时1秒执行，每隔2秒执行一次
        executorService.scheduleWithFixedDelay(new MyRunnable(), 1, 2, TimeUnit.SECONDS);
        System.out.println("y = " + System.currentTimeMillis());
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("begin:" + System.currentTimeMillis() + ", name: " + Thread.currentThread().getName());
                //线程休眠3秒再执行
                TimeUnit.SECONDS.sleep(3);
                System.out.println("end:  " + System.currentTimeMillis() + ", name: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //3.使用Callable延迟运行
    private static void test3() {
        try {
            List<Callable> callableList = new ArrayList<>();
            callableList.add(new MyCallableA());
            callableList.add(new MyCallableB());

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            //延时2秒执行
            ScheduledFuture futureA = executorService.schedule(callableList.get(0), 2, TimeUnit.SECONDS);
            //延时10秒执行
            ScheduledFuture futureB = executorService.schedule(callableList.get(1), 10, TimeUnit.SECONDS);

            System.out.println("x:" + System.currentTimeMillis());
            System.out.println("返回值A:" + futureA.get());
            System.out.println("返回值B:" + futureB.get());
            System.out.println("y:" + System.currentTimeMillis());

            executorService.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class MyCallableA implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("callA begin:" + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            //线程休眠3秒再执行
            TimeUnit.SECONDS.sleep(3);
            System.out.println("callA end:  " + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            return "returnA";
        }
    }

    static class MyCallableB implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("callB begin:" + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            System.out.println("callB end:  " + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            return "returnB";
        }
    }

}
