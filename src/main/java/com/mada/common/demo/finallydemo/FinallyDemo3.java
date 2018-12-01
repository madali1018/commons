package com.mada.common.demo.finallydemo;

/**
 * Created by madali on 2017/5/1.
 * <p>
 * 如果finally语句中没有return语句覆盖返回值，那么原来的返回值就不会因为finally里的修改而改变。
 */
public class FinallyDemo3 {

    public static void main(String[] args) {
        System.out.println(test3());
    }

    public static int test3() {
        int b = 20;

        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
            b = 150;
        }

        return 2000;
    }
}
