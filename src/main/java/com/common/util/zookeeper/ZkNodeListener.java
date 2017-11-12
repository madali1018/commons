package com.common.util.zookeeper;

/**
 * Created by madali on 2017/4/27.
 */
abstract class ZkNodeListener implements IZkNodeListener {

    private final String path;
    private final String nodeName;

    private ZkNodeListener() {
        this(null);
    }

    protected ZkNodeListener(String path) {
        this.path = path;
        this.nodeName = path.substring(path.lastIndexOf("/") + 1);
    }

    @Override
    public final String getPath() {
        return this.path;
    }

    protected final String getNodeName() {
        return this.nodeName;
    }
}
