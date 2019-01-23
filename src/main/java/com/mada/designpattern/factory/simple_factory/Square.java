package com.mada.designpattern.factory.simple_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
