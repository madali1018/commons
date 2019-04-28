package com.mada.commons.demo.concurrent.timer.p4;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by madali on 2018/11/21 15:25
 */
public class TimerTest04 {

    private Timer timer;
    public long start;

    public TimerTest04(){
        this.timer = new Timer();
        start = System.currentTimeMillis();
    }

    public void timerOne(){
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerOne invoked ,the time:" + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(4000);    //线程休眠3000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    public void timerTwo(){
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("timerOne invoked ,the time:" + (System.currentTimeMillis() - start));
            }
        }, 3000);
    }

    public static void main(String[] args) throws Exception {
        TimerTest04 test = new TimerTest04();

        test.timerOne();
        test.timerTwo();
    }

}
