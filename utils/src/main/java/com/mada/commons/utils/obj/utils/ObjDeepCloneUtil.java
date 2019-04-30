package com.mada.commons.utils.obj.utils;

import com.alibaba.fastjson.JSON;
import net.sf.cglib.beans.BeanCopier;

import java.io.*;

/**
 * 对象深拷贝
 * <p>
 * Created by madali on 2019/4/29 18:18
 */
public class ObjDeepCloneUtil {

    //方法一：Object类实现Cloneable接口，在clone方法中对list，collection.map，类引用进行处理，即可深克隆

    //方法二：使用FastJson(序列化反序列化)进行深拷贝，fromObject是一个普通的java对象，无需实现Serializable或Cloneable接口
    public static Object byFastJson(Object fromObject) {
        return JSON.parseObject(JSON.toJSONString(fromObject), fromObject.getClass());
    }

    /**
     * 方法三：使用序列化(读写流)进行深拷贝
     * 1.fromObject及其引用类都必须实现Serializable接口
     * 2.性能比fastjson和cglib差
     *
     * @param fromObject
     * @return
     */
    public static Object byIoStream(Object fromObject) {
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
     * 方法四：使用cglib实现深拷贝
     * 1.fromObject是一个普通的java对象，无需实现Serializable或Cloneable接口，但fromObject中的属性必须实现get和set方法
     * 2.fromObject支持字段类型：八种基本类型及其包装类，String，Array，集合，Map，类引用
     *
     * @param fromObject
     * @param <T>
     * @return
     */
    public static <T> T byCglib(T fromObject) {
        T result;
        try {
            result = (T) fromObject.getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        BeanCopier copier = BeanCopier.create(fromObject.getClass(), result.getClass(), true);
        copier.copy(fromObject, result, (value, target, context) -> ObjBaseUtil.deepClone(value));

        return result;
    }

}
