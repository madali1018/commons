package com.common.cglib.demo2;

import com.common.cglib.demo1.TargetInterceptor;
import com.common.cglib.demo1.TargetObject;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * Created by madl on 2017/6/1.
 */
public class TestCglib2 {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);


        //NoOp.INSTANCE：一个空Callback，表示什么操作也不做，代理类直接调用被代理的方法不进行拦截。
        Callback noopCallback = NoOp.INSTANCE;
        //callback1：方法拦截器
        Callback callback1 = new TargetInterceptor();
        //fixedValue：表示锁定方法返回值，无论被代理的方法返回什么值，回调方法都返回固定值。
        Callback fixedValue = new TargetResultFixed();

        // TargetMethodCallbackFilter中：
        // method1返回值0表示：使用Callback数组中的0位callback，即空的Callback，对method1不拦截，
        // method2返回值1表示：使用Callback数组中的1位callback，即callback1，对method2使用callback1进行拦截，
        // method3返回值2表示：使用Callback数组中的2位callback，即fixedValue，对method3使用fixedValue进行拦截。
        Callback[] callbackArray = new Callback[]{noopCallback, callback1, fixedValue};


//        enhancer.setCallback(new TargetInterceptor());
        enhancer.setCallbacks(callbackArray);

        CallbackFilter callbackFilter = new TargetMethodCallbackFilter();
        enhancer.setCallbackFilter(callbackFilter);

        TargetObject proxyObject = (TargetObject) enhancer.create();

//        System.out.println("proxyObject: " + proxyObject);
//        System.out.println("proxyObject.method1(): " + proxyObject.method1("str1"));
//        System.out.println("proxyObject.method2(): " + proxyObject.method2(200));

        //因锁定了回调值为1000，所以此处不会输出300
        System.out.println("proxyObject.method3(): " + proxyObject.method3(300));
    }
}
