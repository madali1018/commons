package com.mada.commons.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class LinuxText implements Text {
    @Override
    public void getWholeText() {
        System.out.println("Inside LinuxText::getWholeText() method.");
    }
}
