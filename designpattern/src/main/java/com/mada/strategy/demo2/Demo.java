package com.mada.strategy.demo2;

/**
 * 策略模式示例 参考：https://www.cnblogs.com/lewis0077/p/5133812.html
 * Created by madali on 2019/5/28 13:54
 */
public class Demo {

    public static void main(String[] args) {
        //创建具体的支付策略
        IPayStrategy rmbStrategy = new RMBPay();
        IPayStrategy dollarStrategy = new DollarPay();

        //准备小王的支付上下文
        PayContext payContext = new PayContext("小王", 30000, rmbStrategy);
        //向小王支付工资
        payContext.pay();

        //准备Jack的支付上下文
        payContext = new PayContext("jack", 10000, dollarStrategy);
        //向Jack支付工资
        payContext.pay();

        // 创建支付到银行账户的支付策略
        IPayStrategy accountStrategy = new AccountPay();
        // 准备带有银行账户的上下文
        payContext = new PayContextWithAccount("小张", 40000, accountStrategy, "1234567890");
        // 向小张的账户支付
        payContext.pay();
    }

}
