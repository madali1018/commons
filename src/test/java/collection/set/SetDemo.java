package collection.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by madali on 2017/11/8.
 */
public class SetDemo {

    public static void main(String[] args) {

        Set<Integer> set = new HashSet<>();
        set.add(111);
        set.add(112);
        set.add(113);
        set.add(111);
        set.add(117);
        set.add(114);

        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println("------------------");

        TreeSet treeSet = new TreeSet(set);
        Iterator iterator2 = treeSet.iterator();
        while (iterator2.hasNext()){
            System.out.println(iterator2.next());
        }

    }

}
