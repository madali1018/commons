package com.mada.commons.demo.threadpool.p3;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.LongAdder;

/**
 * Created by madali on 2019/4/10 17:24
 */
@Data
@Builder
public class CountEntity {

    /**
     * LongAdder比AtomicLong还高效，二者都是线程安全的，区别在于：
     * 1.高并发时前者将对单一变量的CAS操作分散为对数组cells中多个元素的CAS操作，取值时进行求和；
     * 2.在并发较低时仅对base变量进行CAS操作，与AtomicLong类原理相同。
     */
    public LongAdder num1;

    public LongAdder num2;

}
