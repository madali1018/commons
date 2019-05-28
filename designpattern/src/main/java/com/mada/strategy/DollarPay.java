package com.mada.strategy;

/**
 * Created by madali on 2019/5/28 13:54
 */
public class DollarPay implements IPayStrategy {

    // 美元支付策略
    @Override
    public void pay(PayContext payContext) {
        System.out.println("现在给：" + payContext.getUsername() + " 美金支付 " + payContext.getMoney() + "dollar !");

    }

}
