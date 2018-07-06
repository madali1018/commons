package obj.deepclone;

import com.alibaba.fastjson.JSON;
import net.sf.cglib.beans.BeanCopier;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * java对象深克隆工具类
 * <p>
 * Created by madali on 2017/5/17.
 */
public class ObjectDeepCloneUtil {

    //方法一：Object类实现Cloneable接口，在clone方法中对list，map，类引用进行处理，即可深克隆。

    //方法二：使用FastJson(序列化反序列化)进行深拷贝，fromObject是一个普通的java对象，无需实现Serializable或Cloneable接口。
    public static Object deepCloneFastJson(Object fromObject) {

        return JSON.parseObject(JSON.toJSONString(fromObject), fromObject.getClass());
    }

    //方法三：使用序列化(读写流)进行深拷贝，fromObject及其引用类必须实现Serializable接口。
    public static Object deepCloneIOStream(Object fromObject) {

        Object toObject;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            //将对象写到流里
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(fromObject);
            out.flush();

            //从流里读出对象
            in = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            toObject = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return toObject;
    }

    /**
     * 方法四：使用cglib实现深拷贝，fromObject是一个普通的java对象，无需实现Serializable或Cloneable接口。
     * <p>
     * cglib实现的是浅拷贝，若要深拷贝需在copier.copy方法中进行处理。
     * cglib在字节码级别生成了setter和getter拷贝属性值的指令代码，性能优于apache common的BeanUtils。
     * <p>
     * apache common的BeanUtils通过JDK自带的反射机制动态的去get,set从而去转换类，但实现的是浅拷贝。
     *
     * @param fromObject
     * @param toObject
     * @return
     */
    public static Object deepCloneCglib(Object fromObject, Object toObject) {

        BeanCopier copier = BeanCopier.create(fromObject.getClass(), toObject.getClass(), true);

        copier.copy(fromObject, toObject, (value, target, context) -> deepClone(value));

        return toObject;
    }

    /**
     * 使用cglib实现深拷贝，fromObj是一个普通的java对象，无需实现Serializable或Cloneable接口。
     *
     * @param fromObj
     * @param <T>
     * @return
     */
    public static <T> T deepCloneCglib(T fromObj) {

        T toObj;
        try {
            toObj = (T) fromObj.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        BeanCopier copier = BeanCopier.create(fromObj.getClass(), toObj.getClass(), true);

        copier.copy(fromObj, toObj, (value, target, context) -> deepClone(value));

        return toObj;
    }

    //根据value的类型，进行深拷贝处理。八种基本类型及其包装类，String，Array，Collection，Map，类引用
    private static Object deepClone(Object value) {

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

    //cglib深拷贝中对类引用的处理
    private static Object getReflectionObject(Object fromObject) {

        //获取原对象类型
        Class cls = fromObject.getClass();

        //实例化目标对象
        Object toObject;
        try {
            toObject = cls.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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
