package com.common.demo.java8.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Auther: madali
 * @Date: 2018/8/15 15:18
 */
public class ConsumerDemo1 {

    @Test
    public void test1() {
        Foo f = new Foo();
//        f.foo(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) {
//                System.out.println(integer);
//            }
//        });

//        f.foo(integer -> System.out.println(integer));

        f.foo(System.out::println);
    }

    @Test
    public void test2() {
        Consumer consumer1 = o -> System.out.println("consumer1");
        Consumer consumer2 = o -> System.out.println("consumer2");
        consumer1.andThen(consumer2).accept(new Object());
        System.out.println("-------------------------------------");
        consumer2.andThen(consumer1).accept(null);
    }

}

class Foo {

    private static List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public Foo() {
    }

    /**
     * Consumer类包含两个方法，一个accept方法用来对输入的参数进行自定义操作，因为是个抽象方法，所以需要实例化对象的时候进行Override，
     * 另一个andThen方法跟Function的方法一样是一个default方法，已经有内部实现所以不需要用户重写，并且具体功能也跟Function差不多。
     * Consumer的中文意思是消费者，意即通过传递进一个参数来对参数进行操作。
     *
     * @param consumer
     */
    public void foo(Consumer<Integer> consumer) {
        for (int i : list) {
            consumer.accept(i * 2);
        }
    }

}