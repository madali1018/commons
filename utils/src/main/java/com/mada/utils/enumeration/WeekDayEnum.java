package com.mada.utils.enumeration;

/**
 * Created by madali on 2017/4/26.
 */
public enum WeekDayEnum {

    //周日
    Sunday,

    //周一
    Monday,

    //周二
    Tuesday,

    //周三
    Wednesday,

    //周四
    Thursday,

    //周五
    Friday,

    //周六
    Saturday;

    public int value() {
        return super.ordinal();
    }

    public String description() {
        String description;
        if (this == WeekDayEnum.Sunday) {
            description = "sunday";
        } else {
            description = this.name();
        }
        return description;
    }
}
