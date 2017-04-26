package com.common.util.log.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * Created by madl on 2017/4/26.
 */
public class JdkLogUtil {

    /**
     * 获取Logger的公共方法
     *
     * @param cls 要记录日志的类
     * @return
     */
    public static Logger getLogger(Class<?> cls) {

        return getLogger(cls, false);
    }

    /**
     * 获取Logger的公共方法
     *
     * @param cls        要记录日志的类
     * @param expandFlag 是否需要单独为该类记录log到对应的目录
     * @return
     */
    public static Logger getLogger(Class<?> cls, boolean expandFlag) {

        Logger target = LoggerFactory.getLogger(cls);

        if (expandFlag) {

            //将目标类和横切类编织在一起
            JdkInvocationHandler handler = new JdkInvocationHandler(target);

            //创建动态代理对象
            Object object = Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),//目标类的类加载器
                    target.getClass().getInterfaces(),//目标类的接口
                    handler);//横切类

            return (Logger) object;
        }

        return target;
    }

    /**
     * 获取Logger的公共方法
     *
     * @param cls         要记录日志的类
     * @param logFileName 该日志信息要记录到的目的文件的名字
     * @return
     */
    public static Logger getLogger(Class<?> cls, String logFileName) {

        Logger target = LoggerFactory.getLogger(cls);

        //将目标类和横切类编织在一起
        JdkInvocationHandler handler = new JdkInvocationHandler(target, logFileName);

        //创建动态代理对象
        Object object = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),//目标类的类加载器
                target.getClass().getInterfaces(),//目标类的接口
                handler);//横切类

        return (Logger) object;
    }
}
