package com.common.util.balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 含权重和leader的负载均衡算法（该算法中服务集合中所有的元素唯一不重复）
 * Created by madl on 2017/4/26.
 */
public class WeightRoundRobin {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WeightRoundRobin.class);

    private static List<NodeEntity> serverList = new ArrayList<>();//所有服务的集合
    private static List<NodeEntity> nServerList = new ArrayList<>();//所有非leader服务的集合

    private static int size = serverList.size();// 所有的服务集合的大小
    private static int index = -1;// 所有的服务集合中leader服务的下标，默认-1表示所有的服务都是非leader服务，只有通过resetLeader方法才可以重新定义leader服务的下标
    private static int nIndex = 0;// 当前调度的非leader服务集合的下标

    private static int allNum = 0;// 已经处理的总的请求的个数
    private static int leaderNum = 0;//leader服务已经处理的请求的个数

    private static final int WEIGHT = 3;// 权重

    public WeightRoundRobin() {
    }

    /**
     * 添加节点（add操作添加的都是非leader服务）
     *
     * @param server
     */
    public synchronized void addNode(String server) {

        if (server == null || server.isEmpty()) {
            LOGGER.info("Add server fail, server is null.");
            return;
        }

        for (int i = 0; i < size; i++) {
            String str = serverList.get(i).getName();
            if (str.equals(server)) {
                LOGGER.error("Add server fail, server " + server + " is already exists!");
                throw new RuntimeException();
            }
        }

        NodeEntity entity = new NodeEntity(server, false);
        serverList.add(entity);
        LOGGER.info("Add server " + server + " successfully.");
        resetList();
    }

    /**
     * 删除节点
     *
     * @param server
     */
    public synchronized void removeNode(String server) {

        if (server == null || server.isEmpty()) {
            LOGGER.info("Remove server fail, server is null.");
            return;
        }

        Iterator<NodeEntity> iterator = serverList.iterator();

        // 要删除的元素在服务集合中是否存在
        boolean isExist = false;
        // 集合中已经遍历的元素的个数
        int i = 0;

        while (iterator.hasNext()) {

            NodeEntity entity = iterator.next();
            boolean bool = entity.isLeader();

            if (entity.getName().equals(server)) {

                if (bool) {
                    // 如果server是leader服务，则删除server之后集合中所有的服务都是非leader服务，此时需将index置为 - 1，
                    index = -1;
                } else if (i < index) {
                    // 如果server是非leader服务，且server的下标小于leader服务的下标，则index向前移动一位即index = index -1
                    index--;
                } else {
                    // 如果server是非leader服务，且server的下标大于leader服务的下标，则index不变

                }

                iterator.remove();
                isExist = true;
                LOGGER.info("Remove server " + server + " successfully.");
            } else {
                i++;
            }
        }

        if (!isExist) {
            LOGGER.info("Remove server fail: server " + server + " does not exist in the serverList.");
            return;
        }

        resetList();
    }

    /**
     * 设置leader服务（newIndex为leader服务的下标）
     *
     * @param newIndex
     */
    public synchronized void setLeader(int newIndex) {

        if (newIndex <= size - 1) {
            // 将newIndex下标对应的server设为leader服务，并将之前的leader服务设为非leader服务
            index = newIndex;
            serverList.get(index).setLeader(true);
            LOGGER.info("Set leader: " + newIndex + " successfully.");
            resetList();
        } else {
            LOGGER.error("Set leader fail, " + newIndex + " out of serverList's size, please reset the newIndex.");
            throw new RuntimeException();
        }
    }

    /**
     * 设置leader服务（server为leader服务）
     *
     * @param server
     */
    public synchronized void setLeader(String server) {

        if (server == null || server.isEmpty()) {
            LOGGER.info("Set leader fail, server is null.");
            return;
        }

        boolean isExist = false;

        for (int i = 0; i < size; i++) {
            NodeEntity entity = serverList.get(i);
            if (entity.getName().equals(server)) {
                index = i;
                entity.setLeader(true);
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            LOGGER.info("Set leader fail, server " + server + " does not exist in the serverList.");
            return;
        }

        resetList();
    }

    private void resetList() {

        // add remove服务后，服务集合的大小发生了变化，size值也需要同步更新
        size = serverList.size();

        // 清空原来的所有非leader服务的集合
        nServerList = new ArrayList<>();

        // 重置nServerList
        for (int i = 0; i < size; i++) {
            if (i != index) {
                NodeEntity entity = serverList.get(i);
                entity.setLeader(false);
                nServerList.add(entity);
            }
        }

        // 重置serverList
        for (int i = 0; i < size; i++) {
            NodeEntity entity = serverList.get(i);
            if (i != index)
                entity.setLeader(false);
        }

        // 清空原先的统计数据，重新记录数据，分配服务
        nIndex = 0;
        leaderNum = 0;
        allNum = 0;

//        LOGGER.debug("==========================================");
    }

    /**
     * 获取下一个请求的server服务
     *
     * @return
     */
    public synchronized String next() {

        NodeEntity entity = new NodeEntity();
        boolean isLeader;
        int nSize = 0;

        if (size == 0) {
            return entity.getName();
        }

        // leader服务的下标超出serverList的大小，报错
        if (size <= index) {
            LOGGER.error(index + " out of serverList's size, please reset the index.");
            throw new RuntimeException();
        }

        // 没有leader服务时（循环分配所有的非leader服务）
        if (index == -1) {

            while (true) {
                nIndex = allNum % size;
                entity = serverList.get(nIndex);

                LOGGER.info("allNum: " + allNum + ", nIndex: " + nIndex);
                allNum++;

                break;
            }
        }

        if (index == 0) {

            // 有leader服务，并且第一个服务即为leader服务时

            // 第一次请求时，已经处理的总的请求数为0，返回第一个服务，
            // 当总的服务数为1时，返回第一个服务
            if (allNum == 0 || size == 1) {

                entity = serverList.get(0);
                isLeader = entity.isLeader();
                leaderNum++;

                LOGGER.info("allNum: " + allNum + ", index: " + index + ", leaderNum: " + leaderNum + ", isLeader: " + isLeader);
                allNum++;
            } else if (size >= 2) {

                // 已经处理的总的服务数量 除以 权重和服务列表的大小的乘积 等于 已经处理的leader服务的总数量。即余数为0时，返回leader服务
                if (allNum % (WEIGHT * size) == 0) {

                    entity = serverList.get(index);
                    isLeader = entity.isLeader();
                    leaderNum++;

                    LOGGER.info("allNum: " + allNum + ", index: " + index + ", leaderNum: " + leaderNum + ", isLeader: " + isLeader);
                    allNum++;
                } else {

                    nSize = nServerList.size();

                    if (nIndex > nSize - 1) {
                        nIndex = 0;
                    }

                    entity = nServerList.get(nIndex);
                    LOGGER.info("allNum: " + allNum + ", nIndex: " + nIndex);

                    nIndex++;
                    allNum++;
                }
            }
        } else if (index > 0) {

            //有leader服务，但leader服务不是第一个服务时

            // 第一次请求时，已经处理的总的请求数为0，返回第一个服务，
            // 当总的服务数为1时，返回第一个服务
            if (allNum == 0 || size == 1) {

                entity = serverList.get(0);
                LOGGER.info("allNum: " + allNum);

                allNum++;
            } else if (size >= 2) {

                if (allNum % (WEIGHT * size) == 0) {

                    entity = serverList.get(index);
                    isLeader = entity.isLeader();
                    leaderNum++;
                    LOGGER.info("allNum: " + allNum + ", index: " + index + ", leaderNum: " + leaderNum + ", isLeader: " + isLeader);

                    allNum++;
                } else {

                    nSize = nServerList.size();

                    // 非leader服务已经循环分配了一圈，重置下标nonIndex为0
                    if (nIndex > nSize - 1) {
                        nIndex = 0;
                        entity = nServerList.get(0);
                    }

                    if (nIndex < nSize - 1) {
                        // 因第一个非leader服务已经分配出去了，此处返回下标+1 的非leader服务。
                        entity = nServerList.get(nIndex + 1);
                    } else if (nIndex == nSize - 1) {
                        // 所有的非leader服务分配完毕，从头开始继续循环分配
                        entity = nServerList.get(0);
                    }

                    LOGGER.info("allNum: " + allNum + ", nIndex: " + nIndex);

                    nIndex++;
                    allNum++;
                }
            }
        }

        return entity.getName();
    }

    private class NodeEntity {

        // server服务名称
        private String name;
        // 该server服务是否是leader服务
        private boolean isLeader;

        public NodeEntity() {
        }

        public NodeEntity(String name, boolean isLeader) {
            this.name = name;
            this.isLeader = isLeader;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isLeader() {
            return isLeader;
        }

        public void setLeader(boolean isLeader) {
            this.isLeader = isLeader;
        }
    }
}