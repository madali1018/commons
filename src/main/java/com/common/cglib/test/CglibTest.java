package com.common.cglib.test;

/**
 * Created by madali on 2017/4/12.
 */
public class CglibTest {

    public static void main(String[] args) {

        BookService target = new BookService();

        CglibMethodInterceptor interceptor = new CglibMethodInterceptor(target);
        BookService proxy = (BookService) interceptor.createProxy();

        proxy.addBook();
    }
}
