package com.mada.utils.coder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Apache Commons项目中用来处理常用的编码方法的工具类包，例如DES、SHA1、MD5、Base64，URL，Soundx等等
 * <p>
 * Created by madali on 2019/10/14 15:55
 */
public class CoderUtil {

    /**
     * Md5加密
     *
     * @param str
     * @return
     */
    public static String md5Encode(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * Base64加密
     *
     * @param str
     * @return
     */
    public static String base64Encode(String str) {
        byte[] b = Base64.encodeBase64(str.getBytes(), true);
        return new String(b);
    }

    /**
     * Base64解密
     *
     * @param str
     * @return
     */
    public static String base64Decode(String str) {
        byte[] b = Base64.decodeBase64(str.getBytes());
        return new String(b);
    }

    /**
     * 生成SHA1
     */
    public static String sha1Encode(String str) {
        return DigestUtils.sha1Hex(str);
    }

    public static void main(String[] args) {
        String s = "加密XYSZ*&)12_=09ew234";

        // MD5加密
        System.out.println(md5Encode(s));
        // Base64加密
        System.out.println(base64Encode(s));

        // Base64解密
        System.out.println(base64Decode("5Yqg5a+GWFlTWiomKTEyXz0wOWV3MjM0"));
    }

}
