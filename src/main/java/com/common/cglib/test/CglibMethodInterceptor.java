package com.common.cglib.test;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by madl on 2017/4/12.
 */
public class CglibMethodInterceptor implements MethodInterceptor {

    private Object target;

    public CglibMethodInterceptor(Object target) {
        this.target = target;
    }

    /**
     * 创建代理对象
     *
     * @return
     */
    public Object createProxy() {

        //Enhancer类是CGLib中的一个字节码增强器，对要处理的类进行扩展
        Enhancer enhancer = new Enhancer();

        //设置要代理的目标类，以扩展它的功能，此处将代理类的父类作为目标类
        enhancer.setSuperclass(this.target.getClass());
        //设置单一回调对象，在回调中拦截对目标方法的调用。若有多个回调对象时使用setCallbacks方法，且顺序要与指定的回调索引一致。
        //设置回调函数，对目标类中的每个方法进行拦截
        enhancer.setCallback(this);

//        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
//
//            System.out.println("Before method: " + method.getName());
//
//            Object object = method.invoke(target, args);
//
//            System.out.println("After method: " + method.getName());
//
//            return object;
//        });

        //回调方法过滤
//        CallbackFilter callbackFilter = new CallbackFilter() {
//            @Override
//            public int accept(Method method) {
//
//                if ("addBook".equals(method.getName())) {
//                    System.out.println("addBook");
//                    return 0;
//                } else {
//                    System.out.println("mm.......");
//                }
//
//                return 0;
//            }
//        };
//
//        enhancer.setCallbackFilter(callbackFilter);


        //创建代理对象
        return enhancer.create();
    }

    //调用目标方法时，CGLib会调用MethodInterceptor接口方法进行拦截，来实现自定义代理逻辑。

    /**
     * @param obj    目标对象
     * @param method 目标方法
     * @param args   参数
     * @param proxy  cglib方法代理对象
     * @return 从代理实例的方法调用返回的值
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("Before method: " + method.getName());

        //动态生成的类是子类或者实现类，因此invokeSuper方法就是执行父类中方法的意思。
        //调用代理类实例上的proxy方法的父类方法（即BookService类中对应的方法）
        Object object = proxy.invokeSuper(obj, args);
        //invoke执行生成子类的方法，但该方法会发生内存溢出，因为子类的方法不断进入intercept方法，而这个方法又去调用子类的方法，两个方法直接循环调用了
//        Object object = proxy.invoke(obj, args);

        System.out.println("After method: " + method.getName());

        return object;
    }
}
