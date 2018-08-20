package java8.concurrent.timer;

import java.util.Date;
import java.util.Timer;

/**
 * @Auther: madali
 * @Date: 2018/7/10 20:07
 */
public class TimerIsDaemon {

    public static void main(String[] args) {

        Timer timer = new Timer(true);
        MyTask task = new MyTask();

        // 程序运行结束后迅速结束当前进程，并且TimeTask中的任务不再执行，因为进程已经结束了。
        timer.schedule(task, new Date());
    }

}
