package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:03
 */
public class SilverStrategy implements IStrategy {

    @Override
    public double compute(long money) {
        System.out.println("白银会员优惠50元");
        return money - 50;
    }

    @Override
    public int getType() {
        return UserTypeEnum.SILVER_VIP.getCode();
    }

}
