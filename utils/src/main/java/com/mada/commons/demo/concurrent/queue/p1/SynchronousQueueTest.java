package com.mada.commons.demo.concurrent.queue.p1;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by madali on 2019/1/5 10:46
 */
public class SynchronousQueueTest {

    /**
     * 1.SynchronousQueue是一个没有数据缓冲的BlockingQueue(队列只能存储一个元素)，生产者线程对其的插入操作put必须等待消费者的移除操作take，反过来也一样，消费者移除数据操作必须等待生产者的插入。
     * 2.SynchronousQueue设置为公平存取策略时，等待线程以FIFO的顺序竞争访问，设置为非公平时，顺序是乱序的。
     *
     * @param args
     */
    public static void main(String[] args) {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>(); // 默认不指定的话是false，不公平的
        SynchronousQueue<Integer> synchronousQueue2 = new SynchronousQueue<>(true);

        new Thread(() -> {
            while (true) {
                try {
                    int number = new Random().nextInt(100);
                    synchronousQueue.put(number);
                    System.out.println(OffsetDateTime.now() + "添加操作put运行完毕,number:" + number);//是操作完毕，并不是添加或获取元素成功!
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println(OffsetDateTime.now() + "获取操作take运行完毕,number:" + synchronousQueue.take());//是操作完毕，并不是添加或获取元素成功!
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
