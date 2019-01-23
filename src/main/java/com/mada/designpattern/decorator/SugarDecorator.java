package com.mada.designpattern.decorator;

/**
 * Created by madali on 2018/5/2.
 */
public class SugarDecorator extends Decorator {

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int getPrice() {
        return mCoffee.getPrice() + 2;
    }

    @Override
    public String getName() {
        return "addSugar";
    }
}