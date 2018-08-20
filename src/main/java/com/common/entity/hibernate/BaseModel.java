package com.common.entity.hibernate;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by madali on 2017/4/27.
 */
public abstract class BaseModel implements Serializable {

    /**
     * 自定义实体和业务实体互转 （先序列化再反序列化）
     *
     * @param obj 源对象
     * @param cls 要转换的目标对象
     * @param <E>
     * @return
     */
    public static <E> E convertTo(Object obj, Class<E> cls) {

        return JSON.parseObject(JSON.toJSONString(obj), cls);
    }
}
