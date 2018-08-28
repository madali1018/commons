package com.mada.common.util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2017/5/2.
 */
public class ThreadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtil.class);

    /**
     * 挂起线程
     *
     * @param thread
     */

    public static void suspend(Thread thread) {

        LOGGER.info("Suspend thread (id: {}; name: {}).", thread.getId(), thread.getName());
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException ie) {
                LOGGER.error(ie.getMessage(), ie);

                throw new RuntimeException(ie);
            }
        }
    }

    /**
     * 恢复线程
     *
     * @param thread
     */
    public static void resume(Thread thread) {
        synchronized (thread) {
            thread.notify();
        }
        LOGGER.info("Resume thread (id: {}; name: {}).", thread.getId(), thread.getName());
    }

//    /**
//     * 获取指定名字的线程（获取满足条件的第一个）
//     * @param name
//     * @return
//     */
//    public static Thread getByName(String name) {
//
//        Thread thread = null;
//
//        Set<Thread> ts =  Thread.getAllStackTraces().keySet();
//
//        for(Thread t : ts) {
//            if(name.equals(t.getName())) {
//                thread = t;
//
//                break;
//            }
//        }
//
//        return thread;
//    }
}
