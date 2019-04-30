package com.mada.commons.utils.balance;

/**
 * Created by madali on 2017/4/26.
 */
public class WeightRoundRobinDemo {

    public static void main(String[] args) {

        long begin = System.currentTimeMillis();

        WeightRoundRobin robin = new WeightRoundRobin();
        for (int i = 0; i < 10; i++) {
            robin.addNode("server10" + i);
        }
        System.out.println("添加服务节点结束...");

//        robin.setLeader(2);
        robin.setLeader("server102");
        System.out.println("设置leader服务节点成功...");

        String serverName;
        for (int i = 0; i < 41; i++) {
            serverName = robin.next();
            System.out.println("获取到的serverName:" + serverName);
        }

        System.out.println("耗时" + (System.currentTimeMillis() - begin) / 1000 + "seconds.");
    }

}