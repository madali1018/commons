package thread;

/**
 * 创建线程的两种方法
 * <p>
 * Created by madali on 2018/3/29.
 */
public class ThreadDemo1 {

    public static void main(String[] args) {

//        create1();
        create2();

    }

    private static void create1() {

        //使用new Thread创建线程。弊端：定义线程的时候就决定了该线程要运行的任务。
        new Thread(() -> {
            System.out.println("使用new Thread创建线程.");
        }).start();

    }

    private static void create2() {
        //使用Runnable接口创建线程。好处：线程就是线程,线程只关心可以并发运行即可,给什么任务就并发运行什么任务.
        Runner runner = new Runner();
        new Thread(runner).start();
    }

}
