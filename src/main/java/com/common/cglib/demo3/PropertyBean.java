package com.common.cglib.demo3;

/**
 * Created by madali on 2017/6/1.
 */
public class PropertyBean {

    private String key;

    private Object value;

    public PropertyBean() {
    }

    public PropertyBean(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropertyBean{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
