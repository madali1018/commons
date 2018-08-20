package collection.map;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2017/11/29.
 */
public class HashTableDemo {

    private static Hashtable hashtable = new Hashtable();

    static {
        hashtable.put("1", "111");
        hashtable.put("2", "222");
        hashtable.put("3", "333");
        hashtable.put("4", "444");
    }

    public static void main(String[] args) {
        test2();
    }

    //通过Enumeration遍历Hashtable的键
    private static void test1() {
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
    }

    //通过Enumeration遍历Hashtable的值
    private static void test2() {
        Enumeration enumeration = hashtable.elements();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
    }

    private static void test3() {
        //返回值为要删除的那个元素的value
        Object object = hashtable.remove("1");
        System.out.println(object);

        hashtable.forEach((key, value) -> System.out.println(key + ", " + value));

//        hashtable.forEach((key, value) -> System.out.println(key + ", " + value));
        Set<Map.Entry> set = hashtable.entrySet();
        for (Map.Entry entry : set) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

}
