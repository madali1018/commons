package com.common.util.zookeeper;

/**
 * Created by madali on 2017/4/27.
 */
interface IZkNodeListener {

    String getPath();

    void onChildAdd(String nodeName, String nodeValue);

    void onChildUpdate(String nodeName, String nodeValue);

    void onChildRemove(String nodeName, String nodeValue);
}
