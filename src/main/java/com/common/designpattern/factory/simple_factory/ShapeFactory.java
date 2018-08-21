package com.common.designpattern.factory.simple_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class ShapeFactory {

    public Shape getShape(String type) {
        if ("circle".equals(type)) {
            return new Circle();
        } else if ("rectangle".equals(type)) {
            return new Rectangle();
        } else if ("square".equals(type)) {
            return new Square();
        } else {
            return null;
        }
    }

}
