package com.mada.utils.dslock.contract;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by madali on 2019/10/24 14:15
 */
public interface DsLock extends Lock, AutoCloseable{

    boolean isOwner();

    @Override
    @SuppressWarnings("all")
    default Condition newCondition() {
        return null;
    }

}
