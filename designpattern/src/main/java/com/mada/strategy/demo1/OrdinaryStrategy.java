package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:03
 */
public class OrdinaryStrategy implements IStrategy {

    @Override
    public double compute(long money) {
        System.out.println("普通会员不打折");
        return money;
    }

    @Override
    public int getType() {
        return UserTypeEnum.ORDINARY.getCode();
    }

}
