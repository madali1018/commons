package balance;

import com.common.util.balance.WeightRoundRobin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2017/4/26.
 */
public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

        WeightRoundRobin robin = new WeightRoundRobin();

        for (int i = 0; i < 10; i++) {
            robin.addNode("10" + i);
        }

        LOGGER.info("Add node success.");

        robin.setLeader(2);
        LOGGER.info("Set leader success.");

        long begin = System.currentTimeMillis();
        String nodeValue;
        for (int i = 0; i < 100000; i++) {

            nodeValue = robin.next();
            LOGGER.info("Get node: " + nodeValue);
        }

        long end = System.currentTimeMillis();
        LOGGER.info("Consumer time " + (end - begin) / 1000 + "seconds.");
    }
}
