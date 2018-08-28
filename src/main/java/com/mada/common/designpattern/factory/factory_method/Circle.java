package com.mada.common.designpattern.factory.factory_method;

/**
 * Created by madali on 2018/5/2.
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
