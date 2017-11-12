package com.common.cglib.demo5;

import net.sf.cglib.proxy.Mixin;

/**
 * Mixin可以对多个对象进行代理，需要同时指定多个接口和多个接口对应的代理对象。
 * <p>
 * Created by madali on 2017/6/1.
 */
public class MixinTest {

    static interface Inter1 {

        void fun1(String arg0);
    }

    static interface Inter2 {

        void fun1(String arg0);

        void fun2(int arg0);
    }

    public static void main(String[] args) {

        //查看编译后的class文件，可以发现CGLIB为Inter1和Inter2这两个代理对象分别生成了一个类
        Mixin mixin = Mixin.create(new Class[]{Inter1.class, Inter2.class},
                new Object[]{

                        new Inter1() {
                            @Override
                            public void fun1(String arg0) {
                                System.out.println("Inter1 - " + arg0);
                            }
                        },

                        new Inter2() {
                            @Override
                            public void fun1(String arg0) {
                                System.out.println("Inter1 - " + arg0);
                            }

                            @Override
                            public void fun2(int arg0) {
                                System.out.println("Inter2 - " + arg0);
                            }
                        }
                }
        );

        Inter1 inter1 = (Inter1) mixin;
        inter1.fun1(" hello ");

        Inter2 inter2 = (Inter2) mixin;
        inter2.fun1(" world ");
        inter2.fun2(900);
    }
}
