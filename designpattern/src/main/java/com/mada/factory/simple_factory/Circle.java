package com.mada.factory.simple_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
