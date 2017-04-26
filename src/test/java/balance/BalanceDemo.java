package balance;

import com.common.util.balance.WeightRoundRobin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by madl on 2017/4/26.
 */
public class BalanceDemo {

    public static void main(String[] args) {

        WeightRoundRobin robin = new WeightRoundRobin();

        Thread thread1 = new Thread(new Thread1(robin));
        thread1.start();

        Thread thread2 = new Thread(new Thread2(robin));
        thread2.start();
    }
}

class Thread1 implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread1.class);

    private WeightRoundRobin robin;

    public Thread1(WeightRoundRobin robin) {
        this.robin = robin;
    }

    @Override
    public void run() {
        try {
            while (true) {
//                System.out.println("Get server: " + robin.next());
                LOGGER.debug("Get server: " + robin.next());
                Thread.sleep(1 * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread2 implements Runnable {

    private WeightRoundRobin robin;

    public Thread2(WeightRoundRobin robin) {
        this.robin = robin;
    }

    private static void print() {

        System.out.println("Please input command:\n" +
                "addNodeXXX: add server XXX\n" +
                "removeNodeXXX: remove server XXX\n" +
                "resetLeaderX: reset leader's index X\n" +
                "?: help\n");
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
                    // setLeader2  setLeader"12"

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
}