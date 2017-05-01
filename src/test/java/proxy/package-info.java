/**
 * Created by madl on 2017/4/26.
 */
package proxy;

//jdk动态代理：代理类和目标类实现共同的接口
//CGLIB动态代理：代理类是目标类的子类

//CGLIB本质上是通过动态的生成一个子类，去覆盖所要代理类中不是final的方法，并设置好callback，
//则原有类的每个方法调用就会转变成调用用户定义的拦截方法(interceptors)。

