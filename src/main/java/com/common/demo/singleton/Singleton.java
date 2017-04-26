package com.common.demo.singleton;

/**
 * 系统中只需要某一个类的唯一对象时才使用单例模式。
 * <p>
 * 饿汉式单例：类加载的时候就初始化     instance方法
 * <p>
 * 懒汉模式：第一次使用时才初始化，延迟  getInstance---getInstance4方法
 * <p>
 * Created by madl on 2017/4/26.
 */
public class Singleton {

    private static final Singleton SINGLETON = new Singleton();

    private static Singleton singleton = null;

    //构造方法私有化，此类不能被继承。防止外界利用构造方法创建多个实例。
    private Singleton() {

        System.out.println("Initialization singleton.");
    }

    //直接返回唯一的实例对象
    public static Singleton instance() {

        return SINGLETON;
    }

    //不加synchronized可能会出现线程安全问题 线程A和B可能会都获得对象。
    public static Singleton getInstance() {

        if (singleton == null)
            singleton = new Singleton();

        return singleton;
    }

    //加了synchronized的getInstance方法
    public synchronized static Singleton getInstance2() {

        if (singleton == null)
            singleton = new Singleton();

        return singleton;
    }

    //getInstance()改进
    public static Singleton getInstance3() {

        synchronized (singleton) {
            if (singleton == null)
                singleton = new Singleton();
        }

        return singleton;
    }

    //getInstance()改进：加锁的同步代码块，安全高效
    public static Singleton getInstance4() {

        if (singleton == null)
            synchronized (Singleton.class) {
                if (singleton == null)
                    singleton = new Singleton();
            }

        return singleton;
    }

    //类中其他方法尽量是static
    public static void print() {

        System.out.println("Other methods.");
    }

    public static void main(String[] args) {

        Singleton singleton = Singleton.instance();
        singleton.print();

        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance2();
        Singleton singleton3 = Singleton.getInstance3();
        Singleton singleton4 = Singleton.getInstance4();

        System.out.println(singleton.equals(singleton1));
        System.out.println(singleton1.equals(singleton2));
        System.out.println(singleton3.equals(singleton4));
    }
}

