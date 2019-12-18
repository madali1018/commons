package com.mada.strategy.demo2;

/**
 * 带银行账户的支付上下文
 * Created by madali on 2019/5/28 13:58
 */
public class PayContextWithAccount extends PayContext {

    //银行账户
    private String account;

    public PayContextWithAccount(String username, double money, IPayStrategy iPayStrategy, String account) {
        super(username, money, iPayStrategy);
        this.account = account;

    }

    public String getAccount() {
        return account;
    }

}
