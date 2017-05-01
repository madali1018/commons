package com.common.entity;

/**
 * Created by madl on 2017/4/27.
 */
public class ZkConfigurationNodeEntity {

    private final String key;
    private final String value;

    public ZkConfigurationNodeEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
