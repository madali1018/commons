package com.mada.utils.balance;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 含权重和leader的负载均衡算法（该算法中服务集合中所有的元素唯一不重复）
 * Created by madali on 2017/4/26.
 */
@Log4j2
public class WeightRoundRobin {

    // 所有服务的集合
    private static List<NodeEntity> serverList = new ArrayList<>();
    // 所有非leader服务的集合
    private static List<NodeEntity> nServerList = new ArrayList<>();

    // 所有的服务集合中leader服务的下标，默认-1表示所有的服务都是非leader服务，只有通过resetLeader方法才可以重新定义leader服务的下标
    private static int index = -1;
    // 当前调度的非leader服务集合的下标
    private static int nIndex = 0;
    // 所有的服务集合的大小
    private static int size = serverList.size();

    // 已经处理的总的请求的个数
    private static int allNum = 0;
    // leader服务已经处理的请求的个数
    private static int leaderNum = 0;

    /**
     * 权重：
     * 可以理解为：已经处理的服务的数量/(权重×服务列表的大小) 为0时，下一个获取的服务是leader服务。
     * <p>
     * 如权重为3，服务列表大小为10，则
     * 已经处理的服务的数量为30,60,90,120...时，下一个获取的服务即为leader服务
     */
    private static final int WEIGHT = 3;

    public WeightRoundRobin() {
    }

    /**
     * 添加节点（add操作添加的都是非leader服务）
     *
     * @param serverName
     */
    public synchronized void addNode(String serverName) {

        if (StringUtils.isEmpty(serverName)) {
            log.warn("空的服务节点不能添加.");
            return;
        }

        for (int i = 0; i < size; i++) {
            String str = serverList.get(i).getName();
            if (str.equals(serverName)) {
                log.error("Add serverName fail, serverName:{} is already exists!", serverName);
                throw new RuntimeException();
            }
        }

        NodeEntity entity = new NodeEntity(serverName, false);
        serverList.add(entity);
        log.info("Add serverName:{} successfully.", serverName);
        resetList();
    }

    /**
     * 删除节点
     *
     * @param serverName
     */
    public synchronized void removeNode(String serverName) {

        if (StringUtils.isEmpty(serverName)) {
            log.warn("空的服务节点不能删除.");
            return;
        }

        Iterator<NodeEntity> iterator = serverList.iterator();

        // 集合中已经遍历的元素的个数
        int i = 0;
        // 要删除的元素在服务集合中是否存在
        boolean isExist = false;

        while (iterator.hasNext()) {
            NodeEntity entity = iterator.next();
            if (serverName.equals(entity.getName())) {
                if (entity.isLeader()) {
                    // 如果server是leader服务，则删除server之后集合中所有的服务都是非leader服务，此时需将index置为 -1，
                    index = -1;
                } else if (i < index) {
                    // 如果server是非leader服务，且server的下标小于leader服务的下标，则index向前移动一位即index = index-1
                    index--;
                } else {
                    // 如果server是非leader服务，且server的下标大于leader服务的下标，则index不变
                }

                iterator.remove();
                isExist = true;
                log.info("Remove serverName:{} successfully.", serverName);
            } else {
                i++;
            }
        }

        if (!isExist) {
            log.info("Remove serverName fail: serverName:{} does not exist in the serverList.", serverName);
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
            log.info("Set leader:{} successfully.", newIndex);
            resetList();
        } else {
            log.error("Set newIndex:{} to leader fail, out of serverList's size, please reset the newIndex.", newIndex);
            throw new RuntimeException();
        }
    }

    /**
     * 设置leader服务（serverName为leader服务）
     *
     * @param serverName
     */
    public synchronized void setLeader(String serverName) {

        if (StringUtils.isEmpty(serverName)) {
            log.info("空的服务节点不能被置为leader.");
            return;
        }

        boolean isExist = false;
        for (int i = 0; i < size; i++) {
            NodeEntity entity = serverList.get(i);
            if (entity.getName().equals(serverName)) {
                index = i;
                entity.setLeader(true);
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            log.info("Set leader fail, serverName:{} does not exist in the serverList.", serverName);
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
    }

    /**
     * 获取下一个请求的server服务
     *
     * @return
     */
    public synchronized String next() {

        NodeEntity entity = new NodeEntity();
        boolean isLeader;
        int nSize;

        if (size == 0) {
            return entity.getName();
        }

        // leader服务的下标超出serverList的大小，报错
        if (size <= index) {
            log.error("index:{} out of serverList's size, please reset the index.", index);
            throw new RuntimeException();
        }

        // 没有leader服务时（循环分配所有的非leader服务）
        if (index == -1) {
            while (true) {
                nIndex = allNum % size;
                entity = serverList.get(nIndex);
                log.info("allNum:{},nIndex:{}.", allNum, nIndex);
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
                log.info("allNum:{},index:{},leaderNum:{},isLeader:{}.", allNum, index, leaderNum, isLeader);
                allNum++;
            } else if (size >= 2) {
                // 已经处理的总的服务数量 除以 权重和服务列表的大小的乘积 等于 已经处理的leader服务的总数量。即余数为0时，返回leader服务
                if (allNum % (WEIGHT * size) == 0) {
                    entity = serverList.get(index);
                    isLeader = entity.isLeader();
                    leaderNum++;
                    log.info("allNum:{},index:{},leaderNum:{},isLeader:{}.", allNum, index, leaderNum, isLeader);
                    allNum++;
                } else {
                    nSize = nServerList.size();
                    if (nIndex > nSize - 1) {
                        nIndex = 0;
                    }
                    entity = nServerList.get(nIndex);
                    log.info("allNum:{},nIndex:{}.", allNum, nIndex);
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
                log.info("allNum:{}", allNum);
                allNum++;
            } else if (size >= 2) {
                if (allNum % (WEIGHT * size) == 0) {
                    entity = serverList.get(index);
                    isLeader = entity.isLeader();
                    leaderNum++;
                    log.info("allNum:{},index:{},leaderNum:{},isLeader:{}.", allNum, index, leaderNum, isLeader);
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

                    log.info("allNum:{},nIndex:{}.", allNum, nIndex);
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