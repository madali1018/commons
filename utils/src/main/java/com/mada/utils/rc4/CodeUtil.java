package com.mada.utils.rc4;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Objects;

/**
 * RC4算法实现的一个加密解密的工具类
 * 1.设定加密时间为5分钟，使用原字符串加密后得到的字符串在5分钟内解密可以得到原字符串，但超过5分钟则不会得到原字符串
 * 2.该工具类中使用的字符串编码格式是utf-8
 * <p>
 * Created by madali on 2019/9/27 14:53
 */
@Log4j2
public class CodeUtil {

    private static final Long FIVE_MINUTE = 5 * 60 * 1000L;
    private static final String DEFAULT_KEY = "j5k7MlsLd58dqfb8n3Sf1gf41gA5hfd9hgw";

    /**
     * 加密
     *
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String encode(String str, String key) throws Exception {
        // 空字符串不加密
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        if (StringUtils.isEmpty(key)) {
            key = DEFAULT_KEY;
        }

        return Rc4Util.encryToStr(String.valueOf(Instant.now().toEpochMilli()), DEFAULT_KEY) + Rc4Util.encryToStr(str, key);
    }

    /**
     * 解密
     * 1.超过五分钟则返回null
     * 2.使用方根据该方法返回值与加密前的原始字符串是否相等，来判断字符串加密是否超过五分钟
     *
     * @param str
     * @param key
     * @return
     */
    public static String decode(String str, String key) {
        // 空字符串或解密的字符串长度不足26，均返回null
        if (StringUtils.isEmpty(str) || (str.length() <= 26)) {
            return null;
        }

        long t1 = Instant.now().toEpochMilli();
        // 当前时间毫秒是13位，加密后是26位
        long t2;
        try {
            t2 = Long.parseLong(Objects.requireNonNull(Rc4Util.decry(str.substring(0, 26), DEFAULT_KEY)));
        } catch (Exception e) {
            log.error("要解密的字符串str:{}", str, e);
            return null;
        }

        // 超过五分钟，返回null
        if ((t1 - t2) > FIVE_MINUTE) {
            return null;
        }

        if (StringUtils.isEmpty(key)) {
            key = DEFAULT_KEY;
        }

        return Rc4Util.decry(str.replaceAll(str.substring(0, 26), ""), key);
    }

    public static void main(String[] args) throws Exception {
        String s = "axa2123fa";
        String s2 = "073b33ac14ef5d53c198312c71bbc20b3505a5bc0369";
        String key = "dsfmdls";
        System.out.println(encode(s, key));
        System.out.println(decode(s2, key));
    }

}
