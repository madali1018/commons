package com.common.demo.cglib.demo1;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by madali on 2017/6/1.
 */
public class TestCglib1 {

    public static void main(String[] args) {

        //Enhancer类是CGLIB中的一个字节码增强器，对要处理的类进行扩展
        Enhancer enhancer = new Enhancer();
        //设置要代理的目标类，以扩展它的功能
        enhancer.setSuperclass(TargetObject.class);
        //设置单一回调对象，对目标类中的每个方法进行拦截。
        // 若有多个回调对象时使用setCallbacks方法，且顺序要与指定的回调索引一致。
        enhancer.setCallback(new TargetInterceptor());
        //创建代理对象
        TargetObject proxyObject = (TargetObject) enhancer.create();

        System.out.println("proxyObject: " + proxyObject);
        System.out.println("proxyObject.method1(): " + proxyObject.method1("str1"));
        System.out.println("proxyObject.method2(): " + proxyObject.method2(200));
        System.out.println("proxyObject.method3(): " + proxyObject.method3(300));
    }
}
