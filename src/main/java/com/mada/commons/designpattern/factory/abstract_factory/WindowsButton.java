package com.mada.commons.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class WindowsButton implements Button {
    @Override
    public void processEvent() {
        System.out.println("Inside WindowsButton::processEvent() method.");
    }
}
