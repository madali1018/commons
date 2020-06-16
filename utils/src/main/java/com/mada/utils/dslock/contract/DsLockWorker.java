package com.mada.utils.dslock.contract;

/**
 * Created by madali on 2019/10/24 14:28
 */
public interface DsLockWorker {

    void onStart();

    void onRelease();

    default void onWaiting() {
    }

    default void onActive() {
    }

}
