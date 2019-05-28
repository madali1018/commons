package com.mada.strategy;

/**
 * 支付策略接口
 * <p>
 * Created by madali on 2019/5/28 11:55
 */
public interface IPayStrategy {

    //支付上下文参数：便于在具体的支付策略中回调上下文中的方法获取数据
    void pay(PayContext payContext);

}
