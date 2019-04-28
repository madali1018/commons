package com.mada.commons.util.balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 负载均衡算法
 * Created by madali on 2017/4/26.
 */
public class RoundRobin {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoundRobin.class);

    //上一次调度下标
    private int index = -1;

    //服务名称集合
    private List<String> serverList = new ArrayList<>();

    /**
     * 添加节点
     *
     * @param serverName
     */
    public synchronized void addNode(String serverName) {
        serverList.add(serverName);
        LOGGER.info("添加服务节点:{}成功", serverName);
    }

    /**
     * 删除节点
     *
     * @param serverName
     */
    public synchronized void removeNode(String serverName) {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).equals(serverName)) {
                serverList.remove(i);
                LOGGER.info("删除服务节点:{}成功", serverName);
                break;
            }
        }
    }

    /**
     * 获取下一个请求的server服务的名称
     *
     * @return
     */
    public synchronized String next() {
        String serverName = null;

        if (serverList.size() == 0) {
            index = -1;
        } else {
            index++;
            if (index >= serverList.size()) {
                index = 0;
            }
            serverName = serverList.get(index);
            LOGGER.info("获取到的服务节点:{}", serverName);
        }

        return serverName;
    }
}
