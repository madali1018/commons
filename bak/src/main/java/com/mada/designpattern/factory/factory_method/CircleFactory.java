package com.mada.designpattern.factory.factory_method;

/**
 * Created by madali on 2018/5/2.
 */
public class CircleFactory implements ShapeFactory {
    @Override
    public Shape getShape() {
        return new Circle();
    }
}
