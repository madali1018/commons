package com.common.demo.proxy.jdkProxy;

import java.lang.reflect.Proxy;

/**
 * Created by madl on 2017/4/26.
 */
public class JdkProxyTest {

    public static void main(String[] args) {

        //希望被代理的目标业务类
        SayService target = new SayServiceImpl();

        //将目标类和横切类编织在一起
        JdkInvocationHandler handler = new JdkInvocationHandler(target);

        //创建动态代理对象
        SayService proxy = (SayService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),//目标类的类加载器
                target.getClass().getInterfaces(),//目标类的接口
                handler
        );//横切类

        proxy.sayHello("111");
        proxy.talking("111");

        proxy.sayHello("222");
        proxy.talking("222");
    }
}
