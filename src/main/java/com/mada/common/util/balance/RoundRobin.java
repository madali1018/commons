package com.mada.common.util.balance;

import java.util.ArrayList;
import java.util.List;

/**
 * 负载均衡算法
 * Created by madali on 2017/4/26.
 */
public class RoundRobin {

    private List<String> serverList = new ArrayList<>();  //服务集合
    private int index = -1;                   //上一次调度下标

    /**
     * 添加节点
     *
     * @param server
     */
    public synchronized void addNode(String server) {

        serverList.add(server);
    }

    /**
     * 删除节点
     *
     * @param server
     */
    public synchronized void removeNode(String server) {

        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).equals(server)) {
                serverList.remove(i);

                break;
            }
        }
    }

    /**
     * 获取下一个请求的server服务
     *
     * @return
     */
    public synchronized String next() {

        String server = null;

        if (serverList.size() == 0)
            index = -1;
        else {
            index++;

            if (index >= serverList.size())
                index = 0;

            server = serverList.get(index);
        }

        return server;
    }
}
