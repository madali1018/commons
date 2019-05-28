package com.mada.strategy;

/**
 * 银行账户支付
 * <p>
 * Created by madali on 2019/5/28 13:56
 */
public class AccountPay implements IPayStrategy {
    @Override
    public void pay(PayContext payContext) {
        PayContextWithAccount ctxAccount = (PayContextWithAccount) payContext;
        System.out.println("现在给：" + ctxAccount.getUsername() + "的账户：" + ctxAccount.getAccount() + " 支付工资：" + ctxAccount.getMoney() + " 元！");
    }
}
