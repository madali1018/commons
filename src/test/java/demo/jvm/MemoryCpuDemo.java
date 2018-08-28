package demo.jvm;

/**
 * CPU 性能分析的主要目的是统计函数的调用情况及执行时间，或者更简单的情况就是统计应用程序的 CPU 使用情况。
 *
 * @Auther: madali
 * @Date: 2018/8/20 17:58
 */
public class MemoryCpuDemo {

    public static void main(String[] args) throws InterruptedException {
        cpuFix();
    }

    /**
     * cpu 运行固定百分比
     *
     * @throws InterruptedException
     */
    public static void cpuFix() throws InterruptedException {
        // 80%的占有率
        int busyTime = 8;
        // 20%的占有率
        int idelTime = 2;
        // 开始时间
        long startTime;

        while (true) {
            // 开始时间
            startTime = System.currentTimeMillis();

            //运行时间
            while (System.currentTimeMillis() - startTime < busyTime) {
            }

            // 休息时间
            Thread.sleep(idelTime);
        }
    }
}
