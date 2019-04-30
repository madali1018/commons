package com.mada.utils.enumeration;

import java.lang.reflect.Method;

/**
 * Created by madali on 2017/4/26.
 */
public class EnumerationUtil {

    /**
     * 获取枚举类的对象
     *
     * @param enumClass 枚举类
     * @param name      枚举类的对象属性
     * @param <T>       枚举类的对象
     * @return 枚举类的对象
     */
    public static <T> T nameOf(Class<T> enumClass, String name) {

        try {
            T returnValue = null;
            Method valuesMethod = enumClass.getMethod("values");
            T[] eArr = (T[]) valuesMethod.invoke(null);

            for (T e : eArr) {
                Method nameMethod = e.getClass().getMethod("name");
                String eName = (String) nameMethod.invoke(e);
                if (name.equalsIgnoreCase(eName)) {
                    returnValue = e;
                    break;
                }
            }

            return returnValue;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

    }

    /**
     * 获取枚举类的对象
     *
     * @param enumClass 枚举类
     * @param value     枚举类的对象属性值
     * @param <T>       枚举类的对象
     * @return 枚举类的对象
     */
    public static <T> T valueOf(Class<T> enumClass, int value) {

        try {
            T returnValue = null;
            Method valuesMethod = enumClass.getMethod("values");
            T[] eArr = (T[]) valuesMethod.invoke(null);

            for (T e : eArr) {
                Method valueMethod = e.getClass().getMethod("value");
                int eValue = (Integer) valueMethod.invoke(e);
                if (value == eValue) {
                    returnValue = e;
                    break;
                }
            }

            return returnValue;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 获取枚举类的对象
     *
     * @param enumClass 枚举类
     * @param value     枚举类的对象属性值
     * @param <T>       枚举类的对象
     * @return 枚举类的对象
     */
    public static <T> T valueOf(Class<T> enumClass, String value) {

        try {
            T returnValue = null;
            Method valuesMethod = enumClass.getMethod("values");
            T[] eArr = (T[]) valuesMethod.invoke(null);

            for (T e : eArr) {
                Method valueMethod = e.getClass().getMethod("value");
                String eValue = (String) valueMethod.invoke(e);
                if (value.equals(eValue)) {
                    returnValue = e;
                    break;
                }
            }

            return returnValue;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
