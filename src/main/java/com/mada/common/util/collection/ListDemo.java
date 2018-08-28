package com.mada.common.util.collection;

import com.mada.common.entity.spring.Student;
import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2018/1/11.
 */
public class ListDemo {

    @Test
    public void test1() {
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Student stu = new Student();
            stu.setName("n_" + i);
            studentList.add(stu);
        }

        //获取studentList中name为n_1的所有元素的集合
        List<Student> stuList = CollectionUtil.list(studentList, Student.class, "name", "n_2");
        System.out.println(stuList);
    }

    @Test
    public void test2() {
        int[] array = {1, 2, 3, 2, 15, 1, 6, 4, 7, 15, 22, 16};
        System.out.println(CollectionUtil.countOccurrences(array, 2));
    }

    @Test
    public void testTreeSet() {
        Set<Integer> set = new HashSet<>();
        set.add(111);
        set.add(112);
        set.add(113);
        set.add(111);
        set.add(117);
        set.add(114);

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("------------------");

        TreeSet treeSet = new TreeSet(set);
        Iterator iterator2 = treeSet.iterator();
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }
    }

}
