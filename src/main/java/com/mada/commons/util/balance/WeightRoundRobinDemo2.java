package com.mada.commons.util.balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Auther: madali
 * @Date: 2018/8/28 18:05
 */
public class WeightRoundRobinDemo2 {

    public static void main(String[] args) {
        WeightRoundRobin robin = new WeightRoundRobin();
        new Thread(new Thread1(robin)).start();
        new Thread(new Thread2(robin)).start();
    }

    private static class Thread1 implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(Thread1.class);

        private WeightRoundRobin robin;

        public Thread1(WeightRoundRobin robin) {
            this.robin = robin;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    LOGGER.debug("Get server: " + robin.next());
                    Thread.sleep(2 * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Thread2 implements Runnable {

        private WeightRoundRobin robin;

        public Thread2(WeightRoundRobin robin) {
            this.robin = robin;
        }

        @Override
        public void run() {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            try {
                while (true) {

                    print();
                    System.out.print("==> ");
                    String line = in.readLine();

                    if (line.startsWith("addNode")) {
                        String server = line.substring(7);
                        robin.addNode(server);
                    } else if (line.startsWith("removeNode")) {
                        String server = line.substring(10);
                        robin.removeNode(server);
                    } else if (line.startsWith("setLeader")) {
                        int newIndex = Integer.parseInt(line.substring(9));
                        robin.setLeader(newIndex);
                    } else if ("".equals(line) || "?".equals(line)) {
                        print();
                    } else {
                        System.out.println("Unknown command: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void print() {
            System.out.println("Please input command:\n" +
                    "addNodeXXX: add server XXX\n" +
                    "removeNodeXXX: remove server XXX\n" +
                    "resetLeaderX: reset leader's index X\n" +
                    "?: help\n");
        }

    }

}
