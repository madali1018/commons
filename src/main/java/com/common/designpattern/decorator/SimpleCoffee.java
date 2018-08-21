package com.common.designpattern.decorator;

/**
 * Created by madali on 2018/5/2.
 */
public class SimpleCoffee extends Coffee {

    private int price = 50;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return "Only coffee";
    }
}
