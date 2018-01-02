package thread;

import com.common.util.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by madali on 2017/12/27.
 */
public class ThreadDemo {

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(111);
        }).start();

        //线程挂起
        ThreadUtil.suspend(new Thread(() -> {
            System.out.println(111);
        }));

        //线程恢复
        ThreadUtil.resume(new Thread(() -> {
            System.out.println(111);
        }));

        //固定线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 5; i++) {
//            System.out.println(i);
            int ii = i;
            fixedThreadPool.execute(() -> {
                System.out.println("Current number is:" + (ii + 1));
            });
        }

    }

}
