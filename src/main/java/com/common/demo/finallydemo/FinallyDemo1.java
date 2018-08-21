package com.common.demo.finallydemo;

/**
 * Created by madali on 2017/5/1.
 * <p>
 * finally语句块中的代码在try或者catch中的return语句执行之后返回之前执行，
 * 且finally中的修改语句不能影响try或者catch中return已经确定的返回值。
 * 如果finally中也有return语句，则覆盖try或者catch中的return直接返回。
 * <p>
 * finally语句在return语句执行之后return返回之前执行的。
 */
public class FinallyDemo1 {

    public static void main(String[] args) {
        System.out.println(test1());
//        System.out.println(test11());
    }

    public static int test1() {
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
        }

        return b;
    }

    public static String test11() {

        try {
            System.out.println("try block");

            return test12();
        } finally {
            System.out.println("finally block");
        }
    }

    public static String test12() {
        System.out.println("return statement");

        return "after return";
    }
}
