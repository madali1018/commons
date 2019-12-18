package com.mada.strategy.demo1;

import com.mada.singleton.Singleton2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2019/12/18 10:09
 */
public class StrategyFactory {

    private Map<Integer, IStrategy> map = new HashMap<>();

    private StrategyFactory() {
        List<IStrategy> strategyList = new ArrayList<>();

        strategyList.add(new OrdinaryStrategy());
        strategyList.add(new SilverStrategy());
        strategyList.add(new GoldStrategy());
        strategyList.add(new PlatinumStrategy());
        strategyList.add(new PlatinumStrategy());

        strategyList.forEach(strategy -> map.put(strategy.getType(), strategy));
    }

    public static StrategyFactory getInstance() {
        return SingleStrategy.singletonInstance;
    }

    private static class SingleStrategy {
        private static StrategyFactory singletonInstance = new StrategyFactory();
    }

    public IStrategy get(Integer type) {
        return map.get(type);
    }

    public static double getResult(long money, int type) {
        if (money < 100) {
            return money;
        }

        IStrategy strategy = StrategyFactory.getInstance().get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("please input right type");
        }

        return strategy.compute(money);
    }

}
