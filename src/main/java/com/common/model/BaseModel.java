package com.common.model;

import com.common.util.json.JsonUtil;

import java.io.Serializable;

/**
 * Created by madl on 2017/4/27.
 */
public abstract class BaseModel implements Serializable {

    public <T> T convertTo(Class<T> cls) {

        return JsonUtil.convertTo(this, cls);
    }
}
