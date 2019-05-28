package com.mada.strategy;

/**
 * Created by madali on 2019/5/28 11:58
 */
public class RMBPay implements IPayStrategy {

    //人民币支付策略
    @Override
    public void pay(PayContext payContext) {
        System.out.println("现在给：" + payContext.getUsername() + " 人民币支付 " + payContext.getMoney() + "元！");
    }

}
