package com.mada.commons.utils.obj.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by madali on 2019/4/29 19:58
 */
public class ObjBaseUtil {

    //根据value的类型，进行深拷贝处理。八种基本类型及其包装类，String，Array，Collection，Map，类引用
    public static Object deepClone(Object value) {
        //null 直接返回
        if (null == value) {
            return null;
        }

        //八种基本类型及其包装类，String，Array 直接返回
        if (value instanceof Byte || value instanceof Short
                || value instanceof Character || value instanceof Integer
                || value instanceof Boolean || value instanceof Long
                || value instanceof Float || value instanceof Double
                || value instanceof String || value.getClass().isArray()) {
            return value;
        }

        // 集合类型
        if (value instanceof Collection) {
            Collection collection = (Collection) value;
            Collection result;
            try {
                result = (Collection) value.getClass().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }

            result.addAll(collection);

            return result;
        }

        //Map类型
        if (value instanceof Map) {
            Map map = (Map) value;
            Map result;

            Set<Map.Entry> set = map.entrySet();
            result = set.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, () -> new HashMap(map.size())));

            return result;
        }

        //类引用
        Object result;
        try {
            result = getReflectionObject(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    //cglib深拷贝中对类引用的处理
    private static Object getReflectionObject(Object fromObj) {
        //获取原对象类型
        Class cls = fromObj.getClass();

        //实例化目标对象
        Object result;
        try {
            result = cls.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        //获取原对象所有属性
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            //属性名
            String fieldName = field.getName();
            if (Objects.equals("serialVersionUID", fieldName)) {
                continue;
            }

            //获取属性的getName和setName
            String fieldGetName = buildFieldGetMethodName(fieldName);
            String fieldSetName = buildFieldSetMethodName(fieldName);
            Method getMethod;
            Method setMethod;
            try {
                getMethod = cls.getMethod(fieldGetName);
                setMethod = cls.getMethod(fieldSetName, new Class[]{field.getType()});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            Object value;
            try {
                //获取原对象的值
                value = getMethod.invoke(fromObj);
                //set到目标对象属性
                setMethod.invoke(result, new Object[]{value});
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    //拼接属性的get方法
    public static String buildFieldGetMethodName(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }

        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    //拼接属性的set方法
    public static String buildFieldSetMethodName(String fieldName) {
        if (StringUtils.isEmpty(fieldName)) {
            return null;
        }

        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    //判断是否存在fieldMethodName方法
    public static boolean checkExistMethod(Method[] methods, String fieldMethodName) {
        return Arrays.stream(methods).anyMatch(method -> fieldMethodName.equals(method.getName()));
    }

}
