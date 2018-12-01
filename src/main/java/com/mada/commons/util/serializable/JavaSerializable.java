package com.mada.commons.util.serializable;

import java.io.*;
import java.util.Objects;

/**
 * Created by madali on 2018/11/28 18:07
 * 注意：
 * 同一个实体，在两个项目中都使用lombok注解
 * 1.使用fastjson进行序列化反序列化都是成功的，即便两个实体的包名不一致。fastjson序列化反序列化是根据@JSONField注解中的name等字段进行处理的。
 * 2.使用io流进行序列化反序列化，包名一致时成功；包名不一致，不成功的：反序列化时根据完整类名找不到对应的实体类。
 */
public class JavaSerializable {

    /**
     * io流实现序列化反序列化
     *
     * @param obj      要序列化的对象
     * @param filename 序列化的byte[]存放的位置
     */
    public static void serializable(Object obj, String filename) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(filename);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(oos)) {
                    oos.flush();
                    oos.close();
                }
                if (Objects.nonNull(fos)) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T deSerializable(Class<T> cls, String filename) {
        FileInputStream fis;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(filename);
            ois = new ObjectInputStream(fis);

            T t = (T) ois.readObject();
            return t;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(ois)) {
                    ois.close();
                }
                if (Objects.nonNull(ois)) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
