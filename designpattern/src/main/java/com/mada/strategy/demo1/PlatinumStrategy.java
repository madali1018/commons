package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:04
 */
public class PlatinumStrategy implements IStrategy {

    @Override
    public double compute(long money) {
        System.out.println("白金会员优惠50元再打7折");
        return (money - 50) * 0.7;
    }

    @Override
    public int getType() {
        return UserTypeEnum.PLATINUM_VIP.getCode();
    }

}
