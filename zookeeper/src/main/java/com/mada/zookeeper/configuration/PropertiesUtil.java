package com.mada.zookeeper.configuration;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: madali
 * @Date: 2018/8/28 20:25
 */
@Slf4j
public class PropertiesUtil {

    private static final String CONFIG_PATH = "zk.properties";

    private static Properties properties = new Properties();

    static {
        try {
            // 读取resources路径下的文件
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("读取zk.properties失败", e);
        }
    }

    public static String getValue(String propertiesKey) {
        return properties.getProperty(propertiesKey);
    }

}
