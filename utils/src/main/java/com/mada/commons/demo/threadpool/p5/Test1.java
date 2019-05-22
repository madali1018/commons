package com.mada.commons.demo.threadpool.p5;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2019/5/22 15:30
 */
public class Test1 {

    // 线程安全的list和set
    private static List<Integer> allList = Collections.synchronizedList(new ArrayList<>());
    private static Set<Integer> allSet = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 100000; i++) {
            executor.execute(new TestTask(i, allList));
        }

        // 等待线程池中线程都执行完毕后再向下执行
        executor.shutdown();
        while (true) {
            try {
                // 每5s执行一次判断操作：线程池中线程是否已完全执行完
                if (executor.awaitTermination(5, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(allList.size());
        for (Integer integer : allList) {
            System.out.println("---------" + integer);
        }
    }

    private static class TestTask implements Runnable {
        private int i;
        private List<Integer> list;

        public TestTask(int i, List<Integer> list) {
            this.i = i;
            this.list = list;
        }

        @Override
        public void run() {
            list.add(i);
        }
    }

}
