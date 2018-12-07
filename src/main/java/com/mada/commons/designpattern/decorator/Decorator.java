package com.mada.commons.designpattern.decorator;

/**
 * Created by madali on 2018/5/2.
 */
public abstract class Decorator extends Coffee {

    protected Coffee mCoffee;

    /**
     * 通过组合的方式把Coffee对象传递进来
     *
     * @param coffee
     */
    public Decorator(Coffee coffee) {
        mCoffee = coffee;
    }
}
