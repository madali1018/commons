package com.common.util.log.jdk;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类（使用的是jdk动态代理）
 * <p>
 * Created by madali on 2017/4/26.
 */
public class JdkInvocationHandler implements InvocationHandler {

    private Object target;
    private String logFileName;

    private ch.qos.logback.classic.Logger debugLog;
    private ch.qos.logback.classic.Logger infoLog;
    private ch.qos.logback.classic.Logger warnLog;
    private ch.qos.logback.classic.Logger errorLog;

    public JdkInvocationHandler(Object target) {
        this.target = target;
    }

    public JdkInvocationHandler(Object target, String logFileName) {
        this.target = target;
        this.logFileName = logFileName;
    }

    /**
     * 拦截对目标类的各个方法的调用
     *
     * @param proxy  代理对象实例
     * @param method 源对象的方法名
     * @param args   传递的方法的实际入参
     * @return
     * @throws Throwable
     */
    @Override
    //TODO 待优化 每次调用debug，info，warn，error方法前都配置appender并加至Logger上，调用完再移除appender，导致效率低下
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //每次调用debug，info，warn，error方法前先配置appender
        FileAppender appender = getAppender((Logger) target, method);

        //将appender加至Logger上
        doBefore(appender, method);

        //目标方法调用
        Object object = method.invoke(target, args);

        //将appender从Logger上移除
        doAfter(appender, method);

        return object;
    }

    private FileAppender getAppender(Logger logger, Method method) {

        FileAppender<ILoggingEvent> appender = new FileAppender<>();

        Level level = null;

        if ("debug".equals(method.getName())) {

            level = Level.DEBUG;
            debugLog = (ch.qos.logback.classic.Logger) logger;
        } else if ("info".equals(method.getName())) {

            level = Level.INFO;
            infoLog = (ch.qos.logback.classic.Logger) logger;
        } else if ("warn".equals(method.getName())) {

            level = Level.WARN;
            warnLog = (ch.qos.logback.classic.Logger) logger;
        } else if ("error".equals(method.getName())) {

            level = Level.ERROR;
            errorLog = (ch.qos.logback.classic.Logger) logger;
        }

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        String[] array = logger.getName().split("\\.");
        String catalog = array[array.length - 1];

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%date{dd-MM-yyyy HH:mm:ss.SSS, GMT} GMT [%thread] [%myFileAndLineConverter] - %msg%n");
        encoder.start();

        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setContext(loggerContext);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        levelFilter.setOnMismatch(FilterReply.DENY);
        levelFilter.start();

        appender.setEncoder(encoder);
        appender.addFilter(levelFilter);
        appender.setContext(loggerContext);

        if (logFileName == null) {
            appender.setFile("log/" + level + "/" + catalog + "/" + catalog + "_" + level + ".log");
        } else {
            appender.setFile("log/" + level + "/" + catalog + "/" + logFileName + "_" + level + ".log");
        }

        appender.start();

        return appender;
    }

    private void doBefore(FileAppender<ILoggingEvent> appender, Method method) {

        if ("debug".equals(method.getName())) {

            if (debugLog != null)
                debugLog.addAppender(appender);
        } else if ("info".equals(method.getName())) {

            if (infoLog != null)
                infoLog.addAppender(appender);
        } else if ("warn".equals(method.getName())) {

            if (warnLog != null)
                warnLog.addAppender(appender);
        } else if ("error".equals(method.getName())) {

            if (errorLog != null)
                errorLog.addAppender(appender);
        }
    }

    private void doAfter(FileAppender<ILoggingEvent> appender, Method method) {

        if ("debug".equals(method.getName())) {

            if (debugLog != null)
                debugLog.detachAppender(appender);
        } else if ("info".equals(method.getName())) {

            if (infoLog != null)
                infoLog.detachAppender(appender);
        } else if ("warn".equals(method.getName())) {

            if (warnLog != null)
                warnLog.detachAppender(appender);
        } else if ("error".equals(method.getName())) {

            if (errorLog != null)
                errorLog.detachAppender(appender);
        }
    }
}
