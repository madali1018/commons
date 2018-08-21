package com.common.designpattern.decorator;

/**
 * Created by madali on 2018/5/2.
 */
public class Test {

    public static void main(String[] args) {
//        getCoffee();
//        getMilk();
//        getSugar();
//        getMilkAndSugar();
//        getTwoMilkAndOneSugar();
        getTwoMilkAndOneSugar2();
    }

    private static void getCoffee() {
        Coffee coffee = new SimpleCoffee();
        //咖啡
        System.out.println(coffee.getName() + "," + coffee.getPrice());
    }

    private static void getMilk() {
        //咖啡
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
    }

    private static void getSugar() {
        Coffee coffee = new SimpleCoffee();
        //咖啡
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+糖
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
    }

    private static void getMilkAndSugar() {
        Coffee coffee = new SimpleCoffee();
        //咖啡
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶+糖
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
    }

    private static void getTwoMilkAndOneSugar() {
        Coffee coffee = new SimpleCoffee();
        //咖啡
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶+牛奶
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶+牛奶+糖
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getName() + "," + coffee.getPrice());
    }

    private static void getTwoMilkAndOneSugar2() {
        Coffee coffee = new SimpleCoffee();
        //咖啡
        System.out.println(coffee.getName() + "," + coffee.getPrice());
        //咖啡+牛奶
        Coffee cAndMilk = new MilkDecorator(coffee);
        System.out.println(cAndMilk.getName() + "," + cAndMilk.getPrice());
        //咖啡+牛奶+牛奶
        Coffee cAndMilkAndMilk = new MilkDecorator(cAndMilk);
        System.out.println(cAndMilkAndMilk.getName() + "," + cAndMilkAndMilk.getPrice());
        //咖啡+牛奶+牛奶+糖
        Coffee cAndMilkAndMilkAndSugar = new SugarDecorator(cAndMilkAndMilk);
        System.out.println(cAndMilkAndMilkAndSugar.getName() + "," + cAndMilkAndMilkAndSugar.getPrice());
    }
}
