package com.common.demo.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by madl on 2017/4/26.
 */
public class JdkInvocationHandler implements InvocationHandler {

    private Object target;

    public JdkInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //目标方法前执行
        System.out.println("---------------------------");
        System.out.println("下一位上台发言");

        //目标方法调用 invoke方法的第一个参数是Proxy的实例，确切的说是@Proxy0的实例
        Object object = method.invoke(target, args);

        //目标方法后执行
        System.out.println("掌声鼓励");

        return object;
    }
}
