package com.mada.commons.demo.concurrent.timer.p3;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created by madali on 2018/11/21 15:17
 */
public class MyTask3 extends TimerTask {

    @Override
    public void run() {
        Date date = new Date(this.scheduledExecutionTime());
        System.out.println("本次执行该线程的时间为:" + date);
    }
}
