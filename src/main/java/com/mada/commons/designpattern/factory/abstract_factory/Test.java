package com.mada.commons.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class Test {
    public static void main(String[] args) {
        AbstractFactory linuxFactory = new LinuxFactory();
        linuxFactory.createButton().processEvent();
        linuxFactory.createText().getWholeText();
        AbstractFactory windowsFactory = new WindowsFactory();
        windowsFactory.createButton().processEvent();
        windowsFactory.createText().getWholeText();
    }
}
