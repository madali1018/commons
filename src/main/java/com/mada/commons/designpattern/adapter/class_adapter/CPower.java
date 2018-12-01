package com.mada.commons.designpattern.adapter.class_adapter;

/**
 * Created by madali on 2018/5/2.
 */
public class CPower extends APower implements ChinaPower{
    @Override
    public void twoStep() {
        this.threeStep();
    }
}
