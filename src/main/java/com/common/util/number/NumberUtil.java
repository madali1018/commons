package com.common.util.number;

import java.text.DecimalFormat;

/**
 * Created by madl on 2017/4/26.
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

        String s = new DecimalFormat(sb.toString()).format(d);

        return s;
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
}
