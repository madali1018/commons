package com.mada.strategy.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by madali on 2019/5/28 11:56
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayContext {

    //员工姓名
    private String username;

    //员工的工资
    private double money;

    //支付策略
    private IPayStrategy iPayStrategy;

    public void pay() {
        //调用具体的支付策略来进行支付
        iPayStrategy.pay(this);
    }

}
