package concurrentAndthread.concurrent.timer;

import java.time.OffsetDateTime;
import java.util.TimerTask;

/**
 * @Auther: madali
 * @Date: 2018/7/10 20:04
 */
public class MyTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("运行了,时间:" + OffsetDateTime.now());
    }
}
