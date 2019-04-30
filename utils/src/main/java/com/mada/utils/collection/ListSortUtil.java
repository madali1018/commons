package com.mada.utils.collection;

import java.util.Arrays;

/**
 * Created by madali on 2019/4/30 14:42
 */
public class ListSortUtil {

    /**
     * 根据指定的数字，对数组进行排序，离指定值越近排序时越靠前，离指定值越远排序时越靠后
     * 示例：4 5 7 6 4 3 10 1 19 21 standard为5
     * 排序后是 5 4 4 6 3 7 1 10 19 21 4和6不分先后顺序
     *
     * @param arr      long int double...都可以，也可以换成对象
     * @param standard 指定值
     * @return
     */
    public static Long[] getSortedArr(Long[] arr, int standard) {
        // 首先直接取得一个临时的绝对值数组
        Long[] temp = new Long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = Math.abs(arr[i] - standard);
        }

        // 然后直接双重for循环取得绝对值最小值和最小值索引，然后把两个数组的值通过索引直接交换
        for (int i = 0; i < arr.length; i++) {
            Long min = temp[i];
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (min > temp[j]) {
                    min = temp[j];
                    minIndex = j;
                }
            }

            Long num = temp[i];
            temp[i] = temp[minIndex];
            temp[minIndex] = num;

            Long tempNum = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tempNum;
        }

        return arr;
    }

    public static void main(String[] args) {
        Long[] arr = {10L, 5L, 4L, 6L, 2L, 8L, 7L, 3L, 1L, 10L, 5L, 8L};
        Arrays.stream(arr).forEach(System.out::println);

        arr = getSortedArr(arr, 5);

        System.out.println("排序后");
        Arrays.stream(arr).forEach(System.out::println);
    }

}
