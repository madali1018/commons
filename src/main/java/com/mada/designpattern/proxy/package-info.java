/**
 * Created by madali on 2017/11/8.
 */
package com.mada.designpattern.proxy;

//代理模式
//JDK动态代理：代理类和目标类实现共同的接口
//CGLIB动态代理：代理类是目标类的子类

//JDK动态代理机制只能代理实现了接口的类，而有些类并没有实现接口，则不能用JDK代理，需要使用CGLIB动态代理。

//CGLIB的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。

//CGLIB本质上是通过动态的生成一个子类，去覆盖所要代理类中不是final的方法，并设置好callback，
//则原有类的每个方法调用就会转变成调用用户定义的拦截方法(interceptors)。



//参考common项目中的日志和对象转换工具：
// https://github.com/madayiban/common/tree/master/src/main/java/com/common/util/log
// https://github.com/madayiban/common/tree/master/src/main/java/com/common/util/obj