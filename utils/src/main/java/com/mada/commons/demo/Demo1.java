package com.mada.commons.demo;

import org.junit.Test;

/**
 * Created by madali on 2019/5/22 16:53
 */
public class Demo1 {

    // 重试N次
    @Test
    public void t1() {
        System.out.println(getNum("101-", 5));
        System.out.println("-----");
        System.out.println(getNum2("1012-", 5));
    }

    public static long getNum(String number, int times) {
        int retryTime = 0;
        boolean returnFlag = false;
        long result = 0;

        while (!returnFlag && retryTime < times) {
            try {
                result = Long.parseLong(number);
                returnFlag = true;
            } catch (NumberFormatException e) {
                System.out.println("retryTime:" + retryTime);
                retryTime++;
            }
        }

        return result;
    }

    public static long getNum2(String number, int retryTime) {
        long result = 0;

        while (retryTime-- > 0) {
            try {
                result = Long.parseLong(number);
            } catch (NumberFormatException e) {
                System.out.println("times:" + retryTime);
            }
        }

        return result;
    }

}
