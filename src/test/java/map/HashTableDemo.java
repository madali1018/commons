package map;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2017/11/29.
 */
public class HashTableDemo {

    public static void main(String[] args) {

        Hashtable hashtable = new Hashtable();
        hashtable.put("1", "111");
        hashtable.put("2", "222");
        hashtable.put("3", "333");
        hashtable.put("4", "444");

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
