package com.mada.proxy.cglib;

/**
 * Created by madali on 2017/4/26.
 */
public class CglibDemo {

    public static void main(String[] args) {

        BookService target = new BookService();

        CglibMethodInterceptor interceptor = new CglibMethodInterceptor(target);
        BookService proxy = (BookService) interceptor.createProxy();

        proxy.addBook();
    }
}
