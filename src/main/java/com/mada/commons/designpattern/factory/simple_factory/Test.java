package com.mada.commons.designpattern.factory.simple_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class Test {

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        shapeFactory.getShape("circle").draw();
        shapeFactory.getShape("rectangle").draw();
        shapeFactory.getShape("square").draw();
    }

}
