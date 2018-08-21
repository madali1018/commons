package com.common.designpattern.adapter.class_adapter;

/**
 * Created by madali on 2018/5/2.
 */
public class Test {

    public static void main(String[] args) {
        ChinaPower chinaPower = new CPower();
        //插入两脚的电源线，可以适配三角的插头。
        chinaPower.twoStep();
    }

}
