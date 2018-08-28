package com.mada.common.designpattern.adapter.object_adapter;

/**
 * Created by madali on 2018/5/2.
 */
public class CPower implements ChinaPower {

    private AmericaPower ap = new APower();

    @Override
    public void twoStep() {
        ap.threeStep();
    }
}
