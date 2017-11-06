package com.common.util.number;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by madali on 2017/11/6.
 */
public class RandomCodeUtil {

    private static final List<String> COLD_LIST;
    private static final Integer CODE_LENGTH = 16;
    private static final String COLD = "abcdefghigklmnopqrstuvwxyz,ABCDEFGHIGKLMNOPQRSTUVWXYZ,0123456789";
//    private static final String COLD = "abcdefghigklmnopqrstuvwxyz,ABCDEFGHIGKLMNOPQRSTUVWXYZ,0123456789,!@#$%^&*()_";

    static {
        COLD_LIST = Arrays.asList(COLD.split(","));
    }

    /**
     * 获取唯一字符串（阿拉伯数字和英文字符串的组合）
     * @return
     */
    public static String getRandomCode() {

        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        while (sb.length() < CODE_LENGTH) {
            String cold = COLD_LIST.get(random.nextInt(COLD_LIST.size()));
            sb.append(cold.charAt(random.nextInt(cold.length())));
        }

        return sb.toString();
    }

}
