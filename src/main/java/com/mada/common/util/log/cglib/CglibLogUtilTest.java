package com.mada.common.util.log.cglib;

import org.slf4j.Logger;

/**
 * Created by madali on 2017/4/26.
 */
public class CglibLogUtilTest {

    //    private static final Logger LOGGER = CglibLogUtil.getLogger(CglibLogUtilTest.class);
//    private static final Logger LOGGER = CglibLogUtil.getLogger(CglibLogUtilTest.class, true);
    private static final Logger LOGGER = CglibLogUtil.getLogger(CglibLogUtilTest.class, "demo");

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            LOGGER.error("LOGGER, i: {}", i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
