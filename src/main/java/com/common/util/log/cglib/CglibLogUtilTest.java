package com.common.util.log.cglib;

import org.slf4j.Logger;

/**
 * Created by madali on 2017/4/26.
 */
public class CglibLogUtilTest {

    private static final Logger LOG = CglibLogUtil.getLogger(CglibLogUtilTest.class);
    private static final Logger LOGGER = CglibLogUtil.getLogger(CglibLogUtilTest.class, true);
    private static final Logger LOGGER2 = CglibLogUtil.getLogger(CglibLogUtilTest.class, "demo");
    private static final Logger LOGGER3 = CglibLogUtil.getLogger(CglibLogUtilTest.class, "demo3");
    private static final Logger LOGGER4 = CglibLogUtil.getLogger(CglibLogUtilTest.class, "demo4");
    private static final Logger LOGGER5 = CglibLogUtil.getLogger(CglibLogUtilTest.class, "demo");

    public static void main(String[] args) {

        LOG.debug("LOG");
        LOG.info("LOG");
        LOG.warn("LOG");
        LOG.error("LOG");

        LOGGER.debug("LOGGER");
        LOGGER.info("LOGGER");
        LOGGER.warn("LOGGER");
        LOGGER.error("LOGGER");

        LOGGER2.debug("LOGGER");
        LOGGER2.info("LOGGER");
        LOGGER2.warn("LOGGER");
        LOGGER2.error("LOGGER");

        LOGGER3.error("LOGGER");
        LOGGER4.error("LOGGER");
        LOGGER5.error("LOGGER");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            LOGGER2.error("LOGGER, i: {}", i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println((end - start) / 1000000.00);
        //100w条日志 231626ms 每条日志 0.231626ms  241864 232138 230918 233731
    }
}
