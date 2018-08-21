package com.common.designpattern.singleton;

/**
 * Created by madali on 2018/4/6.
 */
public class Singleton2 {

    private Singleton2() {
        System.out.println("Singleton2: " + System.nanoTime());
    }

    public static Singleton2 getInstance() {
        return SingletonFactory.singletonInstance;
    }

    /**
     * 利用私有的内部工厂类（线程安全，内部类也可以换成内部接口，不过工厂类变量的作用域要改为public了。
     */
    private static class SingletonFactory {
        private static Singleton2 singletonInstance = new Singleton2();
    }

    public static void main(String[] args) {
        System.out.println(Singleton2.getInstance());
    }

}
