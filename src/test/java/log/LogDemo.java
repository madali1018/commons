package log;

import com.common.util.log.cglib.CglibLogUtil;
import org.slf4j.Logger;

public class LogDemo {

    private static final Logger LOGGER = CglibLogUtil.getLogger(LogDemo.class, true);

    public static void main(String[] args) {

        String clientId = "111222";
        String userId = "343";

        LOGGER.info("根据设备ID:{}查询出的要通知的报警userId:{}", clientId, userId);

    }

}
