package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:02
 */
public interface IStrategy {

    // 计费方法
    double compute(long money);

    // 返回 type
    int getType();

}
