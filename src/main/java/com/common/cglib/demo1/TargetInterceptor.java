package com.common.cglib.demo1;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 目标拦截器，实现MethodInterceptor
 * <p>
 * Created by madl on 2017/6/1.
 */
public class TargetInterceptor implements MethodInterceptor {

    /**
     * 重写方法，拦截在方法前和方法后加入业务
     *
     * @param obj    目标对象
     * @param method 目标方法
     * @param args   参数
     * @param proxy  CGLIB方法代理对象
     * @return 从代理实例的方法调用返回的值
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("调用前------");

        /**
         *  我们看到，在例子中使用的是invokeSuper()方法，
         *  因为动态生成的类是子类或者是实现类，因此invokeSuper就是执行父类中方法的意思（即实体类TargetObject中对应的方法）。
         *
         *  invoke()方法是用于 相同类中的其他对象的方法执行，也就是说这个方法中的obj需要传入相同一个类的另一个对象，
         *  否则会进入无限递归循环。
         */

        //调用代理类实例上的proxy方法的父类方法，即实体类TargetObject中对应的方法。
        Object result = proxy.invokeSuper(obj, args);
        //使用invoke调用方法，会发生和java动态代理一样的无限循环调用
//        Object result = proxy.invoke(obj, args);

        System.out.println("调用后------");

        return result;
    }
}
