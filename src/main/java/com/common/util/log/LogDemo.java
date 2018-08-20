package com.common.util.log;

import com.alibaba.fastjson.JSONObject;
import com.common.util.log.cglib.CglibLogUtil;
import org.slf4j.Logger;

public class LogDemo {

    private static final Logger LOGGER = CglibLogUtil.getLogger(LogDemo.class, true);

    public static void main(String[] args) {

        LOGGER.info("dataType:" + 1111 + ",context:" + 22222 + ",获取到的parserRule:" + "ssss");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("111", "211");
        LOGGER.warn("IoT解析kafka消息:{}并保存事件信息至iot_events表失败,事件为null.", jsonObject);

        String clientId = "111222";
        String userId = "343";
        LOGGER.info("根据设备ID:{}查询出的要通知的报警userId:{}", clientId, userId);

        String startTime = "2017-10-10";
        String endTime = "2017-10-12";
        LOGGER.warn("传入的startTime:" + startTime + "或endTime:" + endTime + "的时间格式不正确,应为yyyy-MM-dd HH:mm:ss");
        LOGGER.warn("传入的startTime:{}或endTime:{}的时间格式不正确,应为yyyy-MM-dd HH:mm:ss", startTime, endTime);
    }

}
