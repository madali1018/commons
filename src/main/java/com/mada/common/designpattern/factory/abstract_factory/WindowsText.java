package com.mada.common.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class WindowsText implements Text {
    @Override
    public void getWholeText() {
        System.out.println("Inside WindowsText::getWholeText() method.");
    }
}
