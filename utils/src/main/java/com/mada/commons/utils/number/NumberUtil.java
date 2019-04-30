package com.mada.commons.utils.number;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by madali on 2017/4/26.
 */
public class NumberUtil {

    /**
     * 数字的四舍五入
     *
     * @param d  需处理的数字
     * @param dp 保留的位数
     * @return
     */
    public static double round(double d, int dp) {

        boolean minus = false;

        if (d < 0) {
            minus = true;

            d *= -1;
        }

        double d1 = Math.round(d * Math.pow(10, dp)) / Math.pow(10, dp);

        if (minus && d1 != 0)
            d1 *= -1;

        return d1;
    }

    /**
     * 基于四舍五入把数字格式化为字符串
     *
     * @param d  需处理的数字
     * @param dp 保留的小数位数
     * @param zr 是否保留小数位为0的位置
     * @return
     */
    public static String format(double d, int dp, boolean zr) {
        if (dp < 0)
            dp = 0;

        //必须为0，不能为#，因为#时，会出现“.XXX”现象
        StringBuffer sb = new StringBuffer("0");
        if (dp > 0) {
            sb.append(".");

            for (int i = 0; i < dp; i++)
                sb.append(zr ? "0" : "#");
        }

        return new DecimalFormat(sb.toString()).format(d);
    }

    /**
     * 数字舍掉
     *
     * @param d
     * @param dp
     * @return
     */
    public static double floor(double d, int dp) {

        boolean minus = false;

        if (d < 0) {
            minus = true;

            d *= -1;
        }

        //优化
        d = round(d, dp + 1);

        //优化
        double d1 = round(d * Math.pow(10, dp), 5);

        d1 = Math.floor(d1) / Math.pow(10, dp);

        //优化
        d1 = round(d1, dp);

        if (minus && d1 != 0)
            d1 *= -1;

        return d1;
    }

    /**
     * 将数字转换为千分位格式(小数点后保留两位小数位，整数位是千分位)
     *
     * @param text
     * @return
     */
    public static String setNumberItem(String text) {

        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");

        if (Double.parseDouble(text) < 0)
            return "-" + decimalFormat.format(-1 * Double.parseDouble(text));
        else
            return decimalFormat.format(Double.parseDouble(text));
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param str
     * @return
     */
    public static String trimZero(String str) {

        if (str.indexOf(".") > 0) {
            //去掉多余的0
            str = str.replaceAll("0+?$", "");
            //如最后一位是.则去掉
            str = str.replaceAll("[.]$", "");
        }

        return str;
    }

    /**
     * 使用java正则表达式去掉小数点后的所有数字，小数点也去掉
     *
     * @param str
     * @return
     */
    public static String trimNumber(String str) {

        if (str.indexOf(".") > 0) {
            //去掉多余的0
            str = str.replaceAll("[0-9]+?$", "");
            //如最后一位是.则去掉
            str = str.replaceAll("[.]$", "");
        }

        return str;
    }

    private static final Map<Character, Long> characterIntegerMap = new HashMap<>();

    static {
        characterIntegerMap.put('0', 0L);
        characterIntegerMap.put('1', 1L);
        characterIntegerMap.put('2', 2L);
        characterIntegerMap.put('3', 3L);
        characterIntegerMap.put('4', 4L);
        characterIntegerMap.put('5', 5L);
        characterIntegerMap.put('6', 6L);
        characterIntegerMap.put('7', 7L);
        characterIntegerMap.put('8', 8L);
        characterIntegerMap.put('9', 9L);
//        characterIntegerMap.put('a', 10L);
        characterIntegerMap.put('b', 10L);
        characterIntegerMap.put('c', 11L);
        characterIntegerMap.put('d', 12L);
        characterIntegerMap.put('e', 13L);
        characterIntegerMap.put('f', 14L);
        characterIntegerMap.put('g', 15L);
        characterIntegerMap.put('h', 16L);
//        characterIntegerMap.put('i', 17L);
        characterIntegerMap.put('j', 17L);
        characterIntegerMap.put('k', 18L);
//        characterIntegerMap.put('l', 21L);
        characterIntegerMap.put('m', 19L);
        characterIntegerMap.put('n', 20L);
//        characterIntegerMap.put('o', 24L);
        characterIntegerMap.put('p', 21L);
        characterIntegerMap.put('q', 22L);
        characterIntegerMap.put('r', 23L);
        characterIntegerMap.put('s', 24L);
        characterIntegerMap.put('t', 25L);
        characterIntegerMap.put('u', 26L);
        characterIntegerMap.put('v', 27L);
        characterIntegerMap.put('w', 28L);
        characterIntegerMap.put('x', 29L);
        characterIntegerMap.put('y', 30L);
        characterIntegerMap.put('z', 31L);
    }

    /**
     * base32转10进制
     * 1.geohash里面没有用到ailo四个字符，故而其他字符按顺序前移，详情见characterIntegerMap
     * 2.str的长度最多可以为12位
     *
     * @param str
     * @return
     */
    public static Long base32To10Number(String str) {
        Long result = 0L;
        if (StringUtils.isEmpty(str) || str.length() > 12 || str.contains("a") || str.contains("i") || str.contains("l") || str.contains("o")) {
            return result;
        }

        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            Long n = characterIntegerMap.get(chars[i]);
            int power = chars.length - 1 - i;
            Long tmp = n * n32nMultipy(power);
            result = result + tmp;
        }

        return result;
    }

    public static Long n32nMultipy(int n) {
        if (0 == n) {
            return 1L;
        } else if (1 == n) {
            return 32L;
        } else if (2 == n) {
            return 32L * 32;
        } else if (3 == n) {
            return 32L * 32 * 32;
        } else if (4 == n) {
            return 32L * 32 * 32 * 32;
        } else if (5 == n) {
            return 32L * 32 * 32 * 32 * 32;
        } else if (6 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32;
        } else if (7 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32 * 32;
        } else if (8 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32 * 32 * 32;
        } else if (9 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32;
        } else if (10 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32;
        } else if (11 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32;
        } else if (12 == n) {
            return 32L * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32 * 32;
        } else {
            return 0L;
        }
    }

    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
        System.out.println(36028797018963968L);

        for (int i = 0; i < 12; i++) {
            System.out.println(n32nMultipy(i));
        }

        System.out.println(base32To10Number("wx4g0u0p5jhq"));

//        System.out.println(base32To10Number("wx4g0wp5sgp6"));
//        System.out.println(base32To10Number("wx4g0wp5krty"));
//        System.out.println(base32To10Number("wx4g0wp5tw2v"));
//        System.out.println(base32To10Number("ybkfc7cw43zt"));
//        System.out.println(Geohash.encode(46.911288, 130.486786));
    }

}
