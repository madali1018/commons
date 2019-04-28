package com.mada.commons.util.collection;

import org.junit.Test;

/**
 * Created by madali on 2018/12/21 20:04
 */
public class TestCollectionUtil {
    
    @Test
    public void sort1() {
        Long[] arr = {10L, 5L, 4L, 6L, 2L, 8L, 7L, 3L, 1L, 10L, 5L, 8L};
        for (Long l : arr) {
            System.out.println(l);
        }
        System.out.println("排序后====================");
        arr = CollectionUtil.getSortedArr(arr, 5);
        for (Long l : arr) {
            System.out.println(l);
        }
    }

    @Test
    public void sort2() {
        int[] arr = {1, 4, 5, 6, 7, 9, 2, 11, 3, 10, 10};
        int[] result = new int[arr.length];
        int a = 5;
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int b = Math.abs(a - arr[i]);
            if (b > max) {
                max = b;
            }
        }

        int z = 0;
        for (int i = 0; i < max + 1; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (Math.abs(a - arr[j]) == i) {
                    result[z] = arr[j];
                    z++;
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

}
