package com.mada.common.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class LinuxFactory implements AbstractFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }

    @Override
    public Text createText() {
        return new LinuxText();
    }
}
