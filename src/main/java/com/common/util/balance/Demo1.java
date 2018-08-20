package com.common.util.balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: madali
 * @Date: 2018/8/20 14:04
 */
public class Demo1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo1.class);

    public static void main(String[] args) {

        WeightRoundRobin robin = new WeightRoundRobin();
        for (int i = 0; i < 10; i++) {
            robin.addNode("10" + i);
        }
        LOGGER.info("Add node success.");
        robin.setLeader(2);
        LOGGER.info("Set leader success.");

        long begin = System.currentTimeMillis();
        String nodeValue;
        for (int i = 0; i < 100000; i++) {
            nodeValue = robin.next();
            LOGGER.info("Get node: " + nodeValue);
        }

        LOGGER.info("Consumer time " + (System.currentTimeMillis() - begin) / 1000 + "seconds.");
    }

}
