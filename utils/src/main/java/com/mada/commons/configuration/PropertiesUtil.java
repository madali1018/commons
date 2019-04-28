package com.mada.commons.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: madali
 * @Date: 2018/8/28 20:25
 */
public class PropertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 路径说明：
     * path不以’/'开头时，默认是从此类所在的包下取资源；
     * path  以’/'开头时，则是从ClassPath根下获取；
     * <p>
     * 具体文件和代码的位置是，代码在src/main/java目录下，资源文件在src/main/resources/目录下。
     * 会从当前类的目录下去找，这个文件如果不和该类在一个目录下，就找不到。
     * 会从编译后的整个classes目录下去找，maven也会把资源文件打包进classes文件夹，所以可以找到。
     * ClassLoader就是从整个classes文件夹找的，所以前面无需再加/。
     */
    private static final String CONFIG_PATH = "zk.properties";
    private static final String CONFIG_PATH2 = "/config/zk/zk.properties";

    private static Properties properties = new Properties();

    static {
        try {
            // 读取resources路径下的文件
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("读取配置文件失败, ErrorMsg:{}", e.getMessage());
        }
    }

//    static {
//        try {
//            // 读取resources/config路径下的文件
//            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(CONFIG_PATH2);
//            properties.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//            LOGGER.error("读取配置文件失败, ErrorMsg:{}", e.getMessage());
//        }
//    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getValue(String propertiesKey) {
        return properties.getProperty(propertiesKey);
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtil.getProperties());

        String ZOOKEEPER_HOST = PropertiesUtil.getValue("ZOOKEEPER_HOST");
        System.out.println(ZOOKEEPER_HOST);

        Integer SERVER_PORT = Integer.parseInt(PropertiesUtil.getValue("SERVER_PORT"));
        System.out.println(SERVER_PORT);
    }

}
