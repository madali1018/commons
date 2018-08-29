package com.mada.common.configuration;

import com.mada.common.util.zookeeper.ZkUtil;
import com.mada.common.util.zookeeper.entity.ZkConfigurationNodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by madali on 2017/4/26.
 */
public class ConfigurationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUtil.class);

    public static final Integer SERVER_PORT;
    public static final String ZOOKEEPER_HOST;

    private static String SERVER_ID = null;
    private static String SERVER_IP = null;

    static {
        ZOOKEEPER_HOST = PropertiesUtil.getValue("ZOOKEEPER_HOST");
        SERVER_PORT = Integer.valueOf(PropertiesUtil.getValue("SERVER_PORT"));
    }

    public static String getServerId() {

        if (SERVER_ID != null)
            return SERVER_ID;

        StringBuffer sb = new StringBuffer();
        sb.append(ZkUtil.getCurrentService().getZookeeperNodeName());
        sb.append("_");
        sb.append(getServerIp().replace(".", "_"));
        sb.append("_");
        sb.append(SERVER_PORT);

        SERVER_ID = sb.toString();

        return SERVER_ID;
    }

    public static String getServerIp() {

        if (SERVER_IP != null) {
            return SERVER_IP;
        }

        Enumeration all;
        try {
            all = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

        List<String> ipList = new ArrayList<>();
        while (all.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) all.nextElement();
            Enumeration addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress inetAddress = (InetAddress) addresses.nextElement();
                if (inetAddress != null && inetAddress instanceof Inet4Address) {
                    String ip = inetAddress.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        ipList.add(ip);
                    }
                }
            }
        }

        String serverIp = null;
        switch (ipList.size()) {
            case 0:
                break;
            case 1:
                serverIp = ipList.get(0);
                break;
            default:
                ZkConfigurationNodeEntity configurationNodeEntity = null;
                String[] segmentArr = configurationNodeEntity.getValue().split(",");
                for (String segment : segmentArr) {
                    Pattern pattern = Pattern.compile(segment.replace("*", "(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))"));
                    for (String ip : ipList) {
                        Matcher matcher = pattern.matcher(ip);
                        if (matcher.matches()) {
                            serverIp = ip;
                            break;
                        }
                    }
                }

                break;
        }

        if (serverIp != null) {
            SERVER_IP = serverIp;
        }

        return serverIp;
    }

    /**
     * 获取环境变量中的值的公共方法
     *
     * @param str          要获取的环境变量中的值的名字
     * @param cls          要获取的环境变量中的值的类属性
     * @param defaultValue 要获取的环境变量中的值的缺省值（可自己指定值，不一定是某种数据类型的默认初始值）
     * @param <T>
     * @return
     */
    public static <T> T getInitConstants(String str, Class<T> cls, T defaultValue) {

        T value = defaultValue;

        if (str != null || "".equals(str)) {
            if (cls == String.class) {
                value = (T) str;
            } else {
                try {
                    Method method = cls.getMethod("valueOf", String.class);
                    value = (T) method.invoke(null, str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }

    public static void main(String[] args) {

        //redis key是否加密，默认是false(不加密，即能看到明文）
        System.out.println(getInitConstants(System.getenv("IS_ENCRYPTION"), Boolean.class, false));
        //redis存放在哪个库中，默认是0(即放在第一个库中)
        System.out.println(getInitConstants(System.getenv("REDIS_DATABASE_ID"), Integer.class, 0));

        String charStr = getInitConstants(System.getenv("DEFAULT_LIST_DELIMITER"), String.class, null);
        char defaultListDelimiter = charStr == null ? '-' : charStr.charAt(0);
        System.out.println(defaultListDelimiter);
        // 使用apache.commons的读取properties文件方法 需先修改默认的分隔符,为-  这样properties文件中,前就不用添加转移字符\
        // 可读取环境变量中的DEFAULT_LIST_DELIMITER，取其第一个字符为apache.commons的分隔符
//        AbstractConfiguration.setDefaultListDelimiter(defaultListDelimiter);
    }
}
