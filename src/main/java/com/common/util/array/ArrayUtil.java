package com.common.util.array;

import java.util.Arrays;

/**
 * Created by madali on 2018/2/6.
 */
public class ArrayUtil {

    /**
     * 计算数组中某个值出现的次数
     *
     * @param numbers
     * @param value
     * @return
     */
    public static long countOccurrences(int[] numbers, int value) {

        //使用 Arrays.stream().filter().count() 计算等于指定值的值的总数
        return Arrays.stream(numbers)
                .filter(number -> number == value)
                .count();
    }

}
