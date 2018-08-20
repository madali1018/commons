package com.common.util.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by madali on 2018/1/3.
 */
public class CollectionUtil {

    /**
     * 判断Collection是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
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
                .filter(number -> number == value)
                .count();
    }

}
