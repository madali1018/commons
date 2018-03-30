package str;

/**
 * Created by madali on 2018/3/30.
 */
public class RuntimeDemo {

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        // 返回 java 虚拟机将会尝试去获取的最大内存容量
        System.out.println("1.系统最大可用空间: " + convertBytesToM(runtime.maxMemory()) + " MB");
        // 返回 java 虚拟机的总共内存量。这个值将会随着时间变化，具体取决于主机环境。
        System.out.println("1.系统总共内存量: " + convertBytesToM(runtime.totalMemory()) + " MB");
        // 返回java虚拟机可用的内存容量。可以调用 gc 方法来增加 freeMemory
        System.out.println("1.系统可用内存量：" + convertBytesToM(runtime.freeMemory()) + " MB");
        System.out.println("-----------------------------------------------------------");

        String garbageStr = "";
        for (int i = 0; i < 20000; i++) {
            garbageStr += i;
        }
        System.out.println("2.系统最大可用空间: " + convertBytesToM(runtime.maxMemory()) + " MB");
        System.out.println("2.系统总共内存量: " + convertBytesToM(runtime.totalMemory()) + " MB");
        System.out.println("2.系统可用内存量：" + convertBytesToM(runtime.freeMemory()) + " MB");
        System.out.println("-----------------------------------------------------------");

//        runtime.gc();
        System.out.println("3.系统最大可用空间: " + convertBytesToM(runtime.maxMemory()) + " MB");
        System.out.println("3.系统总共内存量: " + convertBytesToM(runtime.totalMemory()) + " MB");
        System.out.println("3.系统可用内存量：" + convertBytesToM(runtime.freeMemory()) + " MB");
    }

    private static long convertBytesToM(long bytes) {
        return bytes/1024/1024;
    }

}
