package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:21
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(StrategyFactory.getResult(10, 1));
        System.out.println(StrategyFactory.getResult(1000, 1));
        System.out.println(StrategyFactory.getResult(1000, 2));
        System.out.println(StrategyFactory.getResult(1000, 3));
        System.out.println(StrategyFactory.getResult(1000, 4));
    }

}
