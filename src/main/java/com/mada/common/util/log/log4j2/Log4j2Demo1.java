package com.mada.common.util.log.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Auther: madali
 * @Date: 2018/11/5 14:20
 */
public class Log4j2Demo1 {

    private static final Logger LOGGER = LogManager.getLogger(Log4j2Demo1.class);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            LOGGER.debug("log4j2测试-debug");
            LOGGER.info("log4j2测试-info");
            LOGGER.warn("log4j2测试-warn");
            LOGGER.error("log4j2测试-error");
        }
    }

}
