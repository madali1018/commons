package com.common.util.log.jdk;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.LoggerFactory;

/**
 * Created by madl on 2017/4/26.
 */
public class LogUtilBak {

    /**
     * 获取Logger的公共方法
     * （该方法除了传入要记录的日志的类名外，还需传入日志级别，
     * 且最终获取的Logger是ch.qos.log.classic.Logger，不是org.slf4j.Logger通用接口，
     * 不够优雅，不推荐使用）
     *
     * @param cls   要记录日志的类
     * @param level 日志级别
     * @return
     */
    public static Logger getLogger(Class<?> cls, Level level) {

        Logger logger = (Logger) LoggerFactory.getLogger(cls);
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%date{dd-MM-yyyy HH:mm:ss.SSS, GMT} GMT [%thread] [%file:%line] - %msg%n");
        encoder.start();

        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setContext(loggerContext);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        levelFilter.setOnMismatch(FilterReply.DENY);
        levelFilter.start();

        FileAppender<ILoggingEvent> appender = new FileAppender<>();
        appender.setEncoder(encoder);
        appender.addFilter(levelFilter);
        appender.setContext(loggerContext);
        appender.setFile("log/" + level + "/" + cls.getSimpleName() + "/" + level + ".log");
        appender.start();

        logger.addAppender(appender);

        return logger;
    }

    private static final Logger LOGGER_DEBUG = LogUtilBak.getLogger(LogUtilBak.class, Level.DEBUG);
    private static final Logger LOGGER_INFO = LogUtilBak.getLogger(LogUtilBak.class, Level.INFO);
    private static final Logger LOGGER_WARN = LogUtilBak.getLogger(LogUtilBak.class, Level.WARN);
    private static final Logger LOGGER_ERROR = LogUtilBak.getLogger(LogUtilBak.class, Level.ERROR);

    public static void main(String[] args) {

        LOGGER_DEBUG.debug("LOGGER_DEBUG.debug");
        LOGGER_INFO.info("LOGGER_INFO.info");
        LOGGER_WARN.warn("LOGGER_WARN.warn");
        LOGGER_ERROR.error("LOGGER_ERROR.error");

    }
}
