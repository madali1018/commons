package com.mada.commons.demo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by madali on 2018/12/5 10:56
 */
public class SafeSeq {

    private AtomicLong count = new AtomicLong(0);

    public void inc() {
        count.incrementAndGet();
    }

    public Long get() {
        return count.longValue();
    }

}
