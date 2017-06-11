package proxy.jdkProxy;

import net.sf.cglib.proxy.Factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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


        //这里可以通过运行结果证明proxy是Proxy的一个实例，这个实例实现了SayService接口
        System.out.println(proxy instanceof Proxy);

        //这里可以看出proxyObject的Class类是$Proxy0,这个$Proxy0类继承了Proxy，实现了SayService接口
        System.out.println("proxy的Class类是：" + proxy.getClass().toString());

        System.out.print("proxy中的属性有：");

        Field[] field = proxy.getClass().getDeclaredFields();
        for (Field f : field) {
            System.out.print(f.getName() + ", ");
        }

        System.out.print("\n" + "proxy中的方法有：");

        Method[] method = proxy.getClass().getDeclaredMethods();

        for (Method m : method) {
            System.out.print(m.getName() + ", ");
        }

        System.out.println("\n" + "proxy的父类是：" + proxy.getClass().getSuperclass());

        System.out.print("\n" + "proxy实现的接口是：");

        Class<?>[] interfaces = proxy.getClass().getInterfaces();

        for (Class<?> i : interfaces) {
            System.out.print(i.getName() + ", ");
        }

        System.out.println("\n\n" + "运行结果为：");
        proxy.sayHello("aaaaaa");
    }
}
