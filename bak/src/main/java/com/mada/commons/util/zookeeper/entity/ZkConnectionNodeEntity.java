package com.mada.commons.util.zookeeper.entity;

import com.mada.commons.enumeration.ServerStateEnum;

/**
 * Created by madali on 2017/4/27.
 */
public class ZkConnectionNodeEntity {

    private final String id;
    private final String ip;
    private final int port;
    private final ServerStateEnum serverStateEnum;

    public ZkConnectionNodeEntity(String id, String ip, int port, ServerStateEnum serverStateEnum) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.serverStateEnum = serverStateEnum;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ServerStateEnum getServerStateEnum() {
        return serverStateEnum;
    }

    public String getId() {
        return id;
    }
}
