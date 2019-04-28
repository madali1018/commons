package com.mada.commons.demo.function;

import org.junit.Test;

import java.util.function.Function;

/**
 * @Auther: madali
 * @Date: 2018/8/15 14:12
 */
public class FunctionDemo1 {

    /**
     * Function类包含四种方法，其中一个抽象方法apply()，两个default方法andThen()和compose()，以及一个静态方法identity()。
     * 实例化Function的时候需要实现其中的apply()方法，apply方法接收一个模板类型作为输入参数，在andThen()和compose()方法里会自动调用apply()方法。
     * andThen方法接收一个Function类的实例，通过andThen可以将任意多个Function的apply方法调用连接起来。
     */
    @Test
    public void test1() {
        Function<String, String> function1 = string -> {
            System.out.println("function1:" + string);
            return string;
        };

        Function<String, String> function2 = string -> {
            System.out.println("function2:" + string);
            return string;
        };

        System.out.println("先执行function1,再执行function2");
        function1.andThen(function2).apply("hello world");
        System.out.println("-----------------");
        System.out.println("先执行function2,再执行function1");
        function1.compose(function2).apply("hello world");
        System.out.println("-----------------");
        System.out.println(Function.identity().apply("identity方法是一个静态方法，作用是返回一个Function对象，返回的对象总是返回它被传入的值。"));
    }

    @Test
    public void test2() {

        int a = 10;
        int b = 20;

        modifyTheValue(a, val -> val + b);
        modifyTheValue(a, val -> val * b);
        modifyTheValue(a, val -> val - b);
        modifyTheValue(a, val -> "sth".length() + val - b);
    }

    private void modifyTheValue(int valueToBeOperated, Function<Integer, Integer> function) {
        int newValue = function.apply(valueToBeOperated);
        System.out.println("newValue:" + newValue);
    }

}
