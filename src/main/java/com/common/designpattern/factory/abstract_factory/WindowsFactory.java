package com.common.designpattern.factory.abstract_factory;

/**
 * Created by madali on 2018/5/2.
 */
public class WindowsFactory implements AbstractFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Text createText() {
        return new WindowsText();
    }
}
