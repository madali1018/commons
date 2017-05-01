package reflect;

import java.lang.reflect.Method;

/**
 * Created by madl on 2017/4/26.
 */
public class RefletDemo {
    public static void main(String[] args) {

        String s1 = "str";
        Class<?> c1 = s1.getClass();
        String simpleName = c1.getSimpleName();

        try {
            Class<?> c2 = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class<?> c3 = String.class;

        try {
            Method m = s1.getClass().getDeclaredMethod("");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}