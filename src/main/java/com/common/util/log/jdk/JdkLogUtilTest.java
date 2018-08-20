package com.common.util.log.jdk;

import org.slf4j.Logger;

/**
 * Created by madali on 2017/4/26.
 */
public class JdkLogUtilTest {

    private static final Logger LOG = JdkLogUtil.getLogger(JdkLogUtilTest.class);
    private static final Logger LOGGER = JdkLogUtil.getLogger(JdkLogUtilTest.class, true);
    private static final Logger LOGGER2 = JdkLogUtil.getLogger(JdkLogUtilTest.class, "test");

    public static void main(String[] args) {

        LOG.debug("LOG");
        LOG.info("LOG");
        LOG.warn("LOG");
        LOG.error("LOG");

        LOGGER.debug("LOGGER");
        LOGGER.info("LOGGER");
        LOGGER.warn("LOGGER");
        LOGGER.error("LOGGER");

        LOGGER2.debug("LOGGER2");
        LOGGER2.info("LOGGER2");
        LOGGER2.warn("LOGGER2");
        LOGGER2.error("LOGGER2");
        LOGGER2.error("LOGGER2");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            LOG.error("error, i: {}.", i);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
//        System.out.println((end - start) / 10000.00);
    }
}
