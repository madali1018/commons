package com.common.demo.proxy.jdkProxy;

/**
 * Created by madl on 2017/4/26.
 */
public class SayServiceImpl implements SayService {

    @Override
    public void sayHello(String name) {

        System.out.println(name + ": hello.");
    }

    @Override
    public void talking(String name) {

        System.out.println(name + ": talking.");
    }
}
