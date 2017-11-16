package set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by madali on 2017/11/8.
 */
public class SetDemo {

    public static void main(String[] args) {

        System.out.println(Integer.MAX_VALUE);

        Set<Integer> set = new HashSet<>();

        set.add(111);
        set.add(112);
        set.add(113);
        set.add(111);

        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }

}
