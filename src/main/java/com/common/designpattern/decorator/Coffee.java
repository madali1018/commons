package com.common.designpattern.decorator;

/**
 * Created by madali on 2018/5/2.
 * 这里Coffee相当于我们的Component，是要装饰的类。
 */
public abstract class Coffee {

    public abstract int getPrice();

    public abstract String getName();
}
