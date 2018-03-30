package str;

import java.util.Objects;

/**
 * Created by madali on 2018/3/20.
 */
public class StringDemo {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        StringBuilder s1 = new StringBuilder("a");
        StringBuilder s2 = new StringBuilder("a");
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);

        StringBuffer s3 = new StringBuffer("b");
        StringBuffer s4 = new StringBuffer("b");
        System.out.println(s3.equals(s4));
        System.out.println(s3 == s4);
    }

}
