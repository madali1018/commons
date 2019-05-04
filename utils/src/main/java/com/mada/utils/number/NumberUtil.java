package com.mada.utils.number;

import java.text.DecimalFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        if (minus && d1 != 0) {
            d1 *= -1;
        }

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
        if (dp < 0) {
            dp = 0;
        }

        //必须为0，不能为#，因为#时，会出现“.XXX”现象
        String sb = "";
        if (dp > 0) {
            sb = IntStream.range(0, dp).mapToObj(i -> zr ? "0" : "#").collect(Collectors.joining("", "0" + ".", ""));
        }

        return new DecimalFormat(sb).format(d);
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
        if (minus && d1 != 0) {
            d1 *= -1;
        }

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

        if (Double.parseDouble(text) < 0) {
            return "-" + decimalFormat.format(-1 * Double.parseDouble(text));
        }

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

}
