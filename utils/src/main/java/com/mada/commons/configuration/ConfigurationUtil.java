package com.mada.commons.configuration;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by madali on 2017/4/26.
 */
public class ConfigurationUtil {

    public static final Integer SERVER_PORT;
    public static final String ZOOKEEPER_HOST;

    static {
        ZOOKEEPER_HOST = PropertiesUtil.getValue("ZOOKEEPER_HOST");
        SERVER_PORT = Integer.parseInt(PropertiesUtil.getValue("SERVER_PORT"));
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

        if (StringUtils.isEmpty(str)) {
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
