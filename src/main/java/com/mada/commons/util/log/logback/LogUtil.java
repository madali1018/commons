package com.mada.commons.util.log.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Auther: madali
 * @Date: 2018/8/28 17:35
 */
public class LogUtil {

    static {
        // 指定logback.xml的路径。此处使用相对路径，即相对于当前类的路径。
        File logbackFile = new File("src/main/resources/logback2.xml");
        if (logbackFile.exists()) {
            System.out.println("当前使用的logback.xml是:" + logbackFile.getPath());
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            try {
                configurator.doConfigure(logbackFile);
            } catch (JoranException e) {
                e.printStackTrace(System.err);
                System.exit(-1);
            }
        }
    }

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

        //生成动态代理类
        if (expandFlag) {

            Object proxy = new CglibMethodInterceptor(target).createProxy();

            return (Logger) proxy;
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

        //生成动态代理类
        Object proxy = new CglibMethodInterceptor(target, logFileName).createProxy();

        return (Logger) proxy;
    }

}

