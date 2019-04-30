package com.mada.utils.thread;

import lombok.extern.log4j.Log4j2;

/**
 * Created by madali on 2017/5/2.
 */
@Log4j2
public class ThreadUtil {

    /**
     * 挂起线程
     *
     * @param thread
     */

    public static void suspend(Thread thread) {
        log.info("Suspend thread (id: {}; name: {}).", thread.getId(), thread.getName());
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException ie) {
                log.error(ie.getMessage(), ie);

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
        log.info("Resume thread (id: {}; name: {}).", thread.getId(), thread.getName());
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
