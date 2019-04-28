package com.mada.commons.demo.threadpool.p3;

/**
 * Created by madali on 2019/4/10 17:26
 */
public class MyTask implements Runnable {

    private int num;
    private CountEntity countEntity;

    public MyTask(int num, CountEntity countEntity) {
        this.num = num;
        this.countEntity = countEntity;
    }

    @Override
    public void run() {
        if (num % 2 == 0) {
            countEntity.num1.increment();
        } else {
            countEntity.num2.increment();
        }
    }

}
