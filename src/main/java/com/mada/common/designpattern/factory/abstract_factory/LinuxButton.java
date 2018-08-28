package com.mada.common.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class LinuxButton implements Button {
    @Override
    public void processEvent() {
        System.out.println("Inside LinuxButton::processEvent() method.");
    }
}
