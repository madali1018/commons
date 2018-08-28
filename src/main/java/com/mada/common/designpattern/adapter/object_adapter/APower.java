package com.mada.common.designpattern.adapter.object_adapter;

/**
 * Created by madali on 2018/5/2.
 */
public class APower implements AmericaPower {
    @Override
    public void threeStep() {
        System.out.println("美国电源，三脚适配器.");
    }
}
