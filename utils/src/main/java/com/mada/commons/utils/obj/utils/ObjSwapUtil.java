package com.mada.commons.utils.obj.utils;

import com.mada.commons.utils.obj.annotation.FieldDescriptionAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2017/5/24.
 */
public class ObjSwapUtil {

    /**
     * 对象转换
     * <p>
     * 自定义注解FieldDescriptionAnnotation，反射获取map   属性在注解中的值 –-> [属性名，属性类型，属性值]
     * 当1和2的属性在注解中的值一致，且属性类型一致时，1覆盖2。
     *
     * @param fromObject
     * @param toObject
     * @throws Exception
     */
    public static void swap(Object fromObject, Object toObject) {
        swap(getFieldAnnotationMap(fromObject), getFieldAnnotationMap(toObject), toObject);
    }

    private static void swap(Map<String, Object[]> fromFieldAnnotationMap, Map<String, Object[]> toFieldAnnotationMap, Object toObject) {
        Set<Map.Entry<String, Object[]>> fromSet = fromFieldAnnotationMap.entrySet();
        Set<Map.Entry<String, Object[]>> toSet = toFieldAnnotationMap.entrySet();

        for (Map.Entry fromEntry : fromSet) {
            for (Map.Entry toEntry : toSet) {
                //对象1 2中属性在注解中的值一致时，1覆盖2
                if (fromEntry.getKey().equals(toEntry.getKey())) {
                    Object[] fromArray = (Object[]) fromEntry.getValue();
                    Object[] toArray = (Object[]) toEntry.getValue();

                    //from属性类型  from属性的值
                    Object fromFieldType = fromArray[1];
                    Object fromFieldValue = fromArray[2];

                    //to属性名 to属性类型
                    Object toFieldName = toArray[0];
                    Object toFieldType = toArray[1];

                    if (fromFieldType.equals(toFieldType)) {
                        cover(fromFieldValue, toFieldName, toObject);
                    }
                }
            }
        }
    }

    //get获取bean的属性在注解中的值 与 [属性名，属性类型，属性值] 的对应关系的map
    // 示例：类A中的age字段：[age, int, 0]
    public static Map<String, Object[]> getFieldAnnotationMap(Object object) {
        Map<String, Object[]> fieldAnnotationMap = new HashMap<>();

        Class<?> cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            //设置字段可见性
            field.setAccessible(true);
            if (!field.isAnnotationPresent(FieldDescriptionAnnotation.class)) {
                continue;
            }

            FieldDescriptionAnnotation fieldDescriptionAnnotation = field.getAnnotation(FieldDescriptionAnnotation.class);
            //获取属性的getName和setName
            String fieldGetName = ObjBaseUtil.buildFieldGetMethodName(field.getName());
            String fieldSetName = ObjBaseUtil.buildFieldSetMethodName(field.getName());
            //属性的get和set方法必须都存在
            if (!ObjBaseUtil.checkExistMethod(methods, fieldGetName) || !ObjBaseUtil.checkExistMethod(methods, fieldSetName)) {
                continue;
            }

            //属性在注解中的值
            String fieldAnnotationValue = fieldDescriptionAnnotation.fieldDescription();

            Object[] array = new Object[3];
            //属性名
            array[0] = field.getName();
            //属性类型
            array[1] = field.getType();

            Method fieldGetMethod;
            try {
                fieldGetMethod = cls.getMethod(fieldGetName, new Class[]{});
                //属性的值
                array[2] = fieldGetMethod.invoke(object, new Object[]{});
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }

            fieldAnnotationMap.put(fieldAnnotationValue, array);
        }

        return fieldAnnotationMap;
    }

    //属性值的覆盖方法
    private static void cover(Object fromFieldValue, Object toFieldName, Object toObject) {
        Class<?> cls = toObject.getClass();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        Class<?> type = null;
        for (Field field : fields) {
            field.setAccessible(true);
            if (toFieldName.equals(field.getName())) {
                type = field.getType();
            }
        }

        //获取属性的getName和setName
        String fieldGetName = ObjBaseUtil.buildFieldGetMethodName((String) toFieldName);
        String fieldSetName = ObjBaseUtil.buildFieldSetMethodName((String) toFieldName);

        //如果属性不存在get和set方法，返回，遍历下一个属性
        if (!ObjBaseUtil.checkExistMethod(methods, fieldGetName) || !ObjBaseUtil.checkExistMethod(methods, fieldSetName)) {
            return;
        }

        Method fieldSetMethod;
        try {
            fieldSetMethod = cls.getMethod(fieldSetName, type);
            // 1中属性值覆盖2中属性值后，对象1和2应该相互独立。即invoke方法中传入的value必须是进行过深拷贝处理后的value
            fieldSetMethod.invoke(toObject, ObjBaseUtil.deepClone(fromFieldValue));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

    }

}
