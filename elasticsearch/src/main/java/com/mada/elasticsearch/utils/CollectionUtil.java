package com.mada.elasticsearch.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by madali on 2019/3/11 15:31
 */
public class CollectionUtil {

    /**
     * 判断Collection是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 遍历输出集合元素
     *
     * @param collection
     */
    public static void loopCollection(Collection<?> collection) {
        collection.stream().forEach(System.out::println);
    }

    /**
     * 获取集合中具有特定属性值的元素的集合
     *
     * @param list      源集合
     * @param cls       元素的类名
     * @param attrName  元素的属性
     * @param attrValue 元素的属性值
     * @param <T>
     * @return
     */
    public static <T> List<T> list(List<T> list, Class<T> cls, String attrName, String attrValue) {

        List<T> tarList = new ArrayList<>();

        for (T attr : list) {
            try {
                Field f = cls.getDeclaredField(attrName);
                f.setAccessible(true);
                if (attrValue.equals(f.get(attr).toString())) {
                    tarList.add(attr);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return tarList;
    }

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
                // 此处IntPredicate接口可以实现计算数组中大于 小于 ... 某个值的元素的个数
//                .filter(number -> number > value)
//                .filter(number -> number != value)
//                .filter(number -> number <= value)
                .filter(number -> number == value)
                .count();
    }

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

}
