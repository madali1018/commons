package com.mada.commons.util.log.logback;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;

public class LogDemo {

    private static final Logger LOGGER1 = LogUtil.getLogger(LogDemo.class);
    private static final Logger LOGGER2 = LogUtil.getLogger(LogDemo.class, "logtest");
    private static final Logger LOGGER3 = LogUtil.getLogger(LogDemo.class, true);

    @Test
    public void test1() {
        LOGGER1.info("dataType:" + 1111 + ",context:" + 22222 + ",获取到的parserRule:" + "ssss");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("111", "211");
        LOGGER1.warn("IoT解析kafka消息:{}并保存事件信息至iot_events表失败,事件为null.", jsonObject);

        String clientId = "111222";
        String userId = "343";
        LOGGER1.info("根据设备ID:{}查询出的要通知的报警userId:{}", clientId, userId);

        String startTime = "2017-10-10";
        String endTime = "2017-10-12";
        LOGGER1.warn("传入的startTime:" + startTime + "或endTime:" + endTime + "的时间格式不正确,应为yyyy-MM-dd HH:mm:ss");
        LOGGER1.warn("传入的startTime:{}或endTime:{}的时间格式不正确,应为yyyy-MM-dd HH:mm:ss", startTime, endTime);
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            LOGGER3.error("LOGGER, i: {}", i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
