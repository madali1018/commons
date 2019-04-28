package com.mada.factory.factory_method;

/**
 * Created by madali on 2018/5/2.
 */
public class Demo {

    public static void main(String[] args) {
        ShapeFactory circleFactory = new CircleFactory();
        circleFactory.getShape().draw();
        ShapeFactory rectangleFactory = new RectangleFactory();
        rectangleFactory.getShape().draw();
        ShapeFactory squareFactory = new SquareFactory();
        squareFactory.getShape().draw();
    }
}
