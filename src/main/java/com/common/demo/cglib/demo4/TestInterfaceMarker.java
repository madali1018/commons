package com.common.demo.cglib.demo4;

import com.common.demo.cglib.demo1.TargetObject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 接口生成器InterfaceMaker
 * <p>
 * Created by madali on 2017/5/31.
 */
public class TestInterfaceMarker {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //InterfaceMaker会动态生成一个接口，该接口包含指定类定义的所有方法。
        InterfaceMaker interfaceMaker = new InterfaceMaker();

        //抽取某个类的方法生成接口
        interfaceMaker.add(TargetObject.class);

        Class<?> targetInterface = interfaceMaker.create();

        for (Method method : targetInterface.getMethods()) {
            System.out.println("method.getName(): " + method.getName());
        }

        //接口代理并设置代理接口方法拦截
        Object object = Enhancer.create(TargetObject.class, new Class[]{targetInterface}, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                if ("method1".equals(method.getName())) {
                    System.out.println("method1------");
                    return "m111111111111111";
                } else if ("method2".equals(method.getName())) {
                    System.out.println("method2------");
                    return 20000;
                } else if ("method3".equals(method.getName())) {
                    System.out.println("method3------");
                    return 300;
                }

                return "default";
            }
        });

        Method targetMethod = object.getClass().getMethod("method1", new Class[]{String.class});
        Object obj = targetMethod.invoke(object, "jkl");
        System.out.println("obj: " + obj);

        Method targetMethod2 = object.getClass().getMethod("method2", new Class[]{int.class});
        int i = (int) targetMethod2.invoke(object, new Object[]{33});
        System.out.println("i: " + i);

        Method targetMethod3 = object.getClass().getMethod("method3", new Class[]{int.class});
        int i2 = (int) targetMethod3.invoke(object, new Object[]{10000});
        System.out.println("i2: " + i2);
    }
}
