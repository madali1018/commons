package com.mada.strategy.demo1;

/**
 * Created by madali on 2019/12/18 10:05
 */
public enum UserTypeEnum {

    ORDINARY(1),

    SILVER_VIP(2),

    PLATINUM_VIP(3),

    GOLD_VIP(4);

    private final int code;

    UserTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
