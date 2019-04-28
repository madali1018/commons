package com.mada.commons.util.balance;

/**
 * @Auther: madali
 * @Date: 2018/8/20 14:04
 */
public class RoundRobinDemo {

    public static void main(String[] args) {

        RoundRobin robin = new RoundRobin();
        for (int i = 0; i < 10; i++) {
            robin.addNode("10" + i);
        }

        robin.removeNode("102");
        System.out.println("------------");

        for (int i = 0; i < 100; i++) {
            System.out.println("serverName:" + robin.next());
        }

    }

}
