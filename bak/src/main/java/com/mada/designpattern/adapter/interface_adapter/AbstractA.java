package com.mada.designpattern.adapter.interface_adapter;

/**
 * Created by madali on 2018/5/2.
 */
public class AbstractA implements A {
    @Override
    public void a1() {

        //此处可以加抽象类中a1()方法的实现
        System.out.println("抽象类AbstractA的a1()方法.");
    }

    @Override
    public void a2() {
        //此处为非空实现，加输出语句只是为了对比测试结果。
        System.out.println("抽象类AbstractA的a1()方法:空实现");
    }

    @Override
    public void a3() {
        //此处为非空实现，加输出语句只是为了对比测试结果。
        System.out.println("抽象类AbstractA的a1()方法:空实现");
    }
}
