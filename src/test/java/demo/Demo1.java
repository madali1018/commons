package demo;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @Auther: madali
 * @Date: 2018/8/22 17:01
 */
public class Demo1 {

    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtils.nextInt(0, 10));
        }
    }

    @Test
    public void test2() {
        String s = "123";
        String s2 = null;
        System.out.println(StringUtils.defaultString(s, "default"));
        System.out.println(StringUtils.defaultString(s2));
        System.out.println("StringUtils.EMPTY:" + StringUtils.EMPTY);
    }

    @Test
    public void test3() {
        // 很长的数字可读性不好，在Java 7中可以使用下划线分隔长int以及long了。
        int i = 1_000_000;
    }

}
