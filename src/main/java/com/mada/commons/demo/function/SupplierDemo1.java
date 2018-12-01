package com.mada.commons.demo.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @Auther: madali
 * @Date: 2018/8/15 15:31
 */
public class SupplierDemo1 {

    /**
     * supplier的中文意思是提供者，跟Consumer类相反，Supplier类用于提供对象，它只有一个get方法，是一个抽象方法，需要编程者自定义想要返回的对象。
     */
    @Test
    public void test1() {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(100);
            }
        };

        Supplier<Integer> supplier2 = () -> new Random().nextInt(100);

        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = supplier.get();
        }
        Arrays.stream(arr).forEach(System.out::println);
    }

}
