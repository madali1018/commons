package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:04
 */
public class GoldStrategy implements IStrategy {

    @Override
    public double compute(long money) {
        System.out.println("黄金会员8折");
        return money * 0.8;
    }

    @Override
    public int getType() {
        return UserTypeEnum.GOLD_VIP.getCode();
    }

}
