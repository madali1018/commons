package com.mada.commons.demo.cglib.demo3;

/**
 * Created by madali on 2017/6/1.
 */
public class CglibDemo {

    public static void main(String[] args) {
        LazyBean lazyBean = new LazyBean("name1", 10);
        System.out.println(lazyBean);
        System.out.println(lazyBean.getName());
        System.out.println(lazyBean.getPropertyBean().getKey());
        System.out.println(lazyBean.getPropertyBean().getValue());
        System.out.println("-----------------------------------------------------------");

        System.out.println(lazyBean.getPropertyBeanDispatcher().getKey());
        System.out.println(lazyBean.getPropertyBeanDispatcher().getValue());
    }
}
