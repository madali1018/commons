package demo;

import java.util.Date;

/**
 * Created by madl on 2017/10/8.
 */
public class NullDemo {

    private static Object object;

    public static void main(String[] args) {

        //java中任何引用变量都将null作为默认值。
        //null只是一种特殊的值，不是对象也不是一种类型，null可以和引用类型互转。
        //任何含有null的包装类在java拆箱生成基本数据类型时都会抛出一个空指针异常。
        //null是任何一个引用变量的默认值，不能使用null引用来调用任何的instance方法或者instance变量。
        String str = null;
        String str2 = (String) null;

        System.out.println(object);
        System.out.println(str instanceof String);//false（null只是一种特殊的值，不是对象也不是一种类型）
        System.out.println("---------------------------");

        String a = new String();
        String b = "";
        String c = null;

        //a分配了内存空间，值存在，但是为空
        if (a.isEmpty())
            System.out.println(1);

        //b分配了内存空间，值存在，但是为空字符串
        if (b.isEmpty())
            System.out.println(2);

        //c未分配内存空间，值存在，但是为null（null是一种特殊的值）
        if (c == null)
            System.out.println(3);

        Date date = new Date();
        //null可以用于判断一个引用类型是否为null，用==来判断
        if (date == null)
            System.out.println("date == null");

        //null可以用于释放内存：让一个非null的引用类型变量指向null，这样这个对象就不再被任何对象应用了，等待JVM垃圾回收机制取回收。
        date = null;
    }
}
