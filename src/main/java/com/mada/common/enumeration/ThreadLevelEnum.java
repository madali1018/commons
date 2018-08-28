package com.mada.common.enumeration;

/**
 * Created by madali on 2017/4/27.
 */
public enum ThreadLevelEnum {

    ERROR(0),   //日志提示、执行事件回调、退出系统
    WARN(1),    //日志提示、执行事件回调
    NORMAL(2);  //日志提示（忽略事件回调）

    private final int value;

    private ThreadLevelEnum(int value) {
        this.value = value;
    }

    public int value() {

        return this.value;
    }

    public String description() {

        return this.name();
    }
}
