package com.common.designpattern.proxy.jdkProxy;

/**
 * Created by madali on 2017/4/26.
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
