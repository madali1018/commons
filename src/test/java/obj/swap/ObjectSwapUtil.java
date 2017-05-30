package obj.swap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * java对象转换工具类（根据自定义注解）
 * Created by madl on 2017/5/17.
 */
public class ObjectSwapUtil {

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

                    //from属性类型
                    Object fromFieldType = fromArray[1];
                    //from属性的值
                    Object fromFieldValue = fromArray[2];

                    //to属性名
                    Object toFieldName = toArray[0];
                    //to属性类型
                    Object toFieldType = toArray[1];

                    if (fromFieldType.equals(toFieldType)) {
                        cover(fromFieldValue, toFieldName, toObject);
                    }

                }

            }

        }

    }

    //get获取bean的属性在注解中的值 与 [属性名，属性类型，属性值] 的对应关系的map
    public static Map<String, Object[]> getFieldAnnotationMap(Object object) {

        Map<String, Object[]> fieldAnnotationMap = new HashMap<>();

        Class<?> cls = object.getClass();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {

            //设置字段可见性
            field.setAccessible(true);

            if (!field.isAnnotationPresent(FieldDescriptionAnnotation.class))
                continue;

            FieldDescriptionAnnotation fieldDescriptionAnnotation = field.getAnnotation(FieldDescriptionAnnotation.class);

            //获取属性的getName和setName
            String fieldGetName = assembleGetName(field.getName());
            String fieldSetName = assembleSetName(field.getName());

            //属性的get和set方法必须都存在
            if (!checkExistMethod(methods, fieldGetName) || !checkExistMethod(methods, fieldSetName))
                continue;

            Method fieldGetMethod;
            try {
                fieldGetMethod = cls.getMethod(fieldGetName, new Class[]{});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            //属性在注解中的值
            String fieldAnnotationValue = fieldDescriptionAnnotation.fieldDescription();

            Object[] array = new Object[3];
            //属性名
            array[0] = field.getName();
            //属性类型
            array[1] = field.getType();

            //属性的值
            try {
                array[2] = fieldGetMethod.invoke(object, new Object[]{});
            } catch (IllegalAccessException | InvocationTargetException e) {
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

            if (toFieldName.equals(field.getName()))
                type = field.getType();
        }

        //获取属性的getName和setName
        String fieldGetName = assembleGetName((String) toFieldName);
        String fieldSetName = assembleSetName((String) toFieldName);

        //如果属性不存在get和set方法，返回，遍历下一个属性
        if (!checkExistMethod(methods, fieldGetName) || !checkExistMethod(methods, fieldSetName))
            return;

        Method fieldSetMethod;
        try {
            fieldSetMethod = cls.getMethod(fieldSetName, type);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 1中属性值覆盖2中属性值后，对象1和2应该相互独立。即invoke方法中传入的value必须是进行过深拷贝处理后的value
        try {
            fieldSetMethod.invoke(toObject, deepClone(fromFieldValue));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    //根据value的类型，进行深拷贝处理。八种基本类型及其包装类，String，Array，Collection，Map，类引用
    private static Object deepClone(Object value) {

        //null 直接返回
        if (null == value) {
            return null;
        }

        //八种基本类型及其包装类，String，Array 直接返回。
        if (value instanceof Byte || value instanceof Short
                || value instanceof Character || value instanceof Integer
                || value instanceof Boolean || value instanceof Long
                || value instanceof Float || value instanceof Double
                || value instanceof String || value.getClass().isArray()) {

            return value;
        } else if (value instanceof Collection) {
            //Collection类型
            Collection collection = (Collection) value;
            Collection copyCollection;

            try {
                copyCollection = (Collection) value.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                copyCollection.add(iterator.next());
            }

            return copyCollection;
        } else if (value instanceof Map) {
            //Map类型
            Map map = (Map) value;
            Map cloneMap = new HashMap(map.size());

            Set<Map.Entry> set = map.entrySet();

            for (Map.Entry entry : set)
                cloneMap.put(entry.getKey(), entry.getValue());

            return cloneMap;
        } else {
            //类引用
            Object object;

            try {
                object = getReflectionObject(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return object;
        }
    }

    //深拷贝中对类引用的处理
    private static Object getReflectionObject(Object fromObject) {

        //获取原对象类型
        Class cls = fromObject.getClass();

        //实例化目标对象
        Object toObject;
        try {
            toObject = cls.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        //获取原对象所有属性
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {

            //判断变量的修饰符
//            System.out.println(Modifier.toString(field.getModifiers()));

            //属性名
            String fieldName = field.getName();

            if ("serialVersionUID" == fieldName)
                continue;
            //获取属性的getName和setName
            String fieldGetName = assembleGetName(fieldName);
            String fieldSetName = assembleSetName(fieldName);

            Method getMethod;
            try {
                getMethod = cls.getMethod(fieldGetName);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            Method setMethod;
            try {
                setMethod = cls.getMethod(fieldSetName, new Class[]{field.getType()});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            //获取原对象的值
            Object value;
            try {
                value = getMethod.invoke(fromObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            //set到目标对象属性
            try {
                setMethod.invoke(toObject, new Object[]{value});
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return toObject;
    }

    //判断是否存在fieldMethodName方法
    private static boolean checkExistMethod(Method[] methods, String fieldMethodName) {

        for (Method method : methods) {

            if (fieldMethodName.equals(method.getName()))
                return true;
        }

        return false;
    }

    //拼接属性的get方法
    private static String assembleGetName(String fieldName) {

        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }

        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    //拼接属性的set方法
    private static String assembleSetName(String fieldName) {

        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }

        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
