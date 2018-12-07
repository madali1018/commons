package com.mada.commons.demo.concurrent.atomic;

/**
 * Created by madali on 2018/12/5 10:56
 */
public class UnSafeSeq {

    private long count = 0;

    public void inc() {
        count++;
    }

    public long get() {
        return count;
    }
}
