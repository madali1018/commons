package com.common.util.collection;

import com.common.entity.spring.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: madali
 * @Date: 2018/8/20 12:42
 */
public class Test1 {

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

}
