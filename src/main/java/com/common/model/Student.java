package com.common.model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by madali on 2017/5/2.
 */
@Component
@Entity
@Table(name = "student")
public class Student extends BaseModel {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "name")
    private String name;

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", age=" + age +
                ", name=" + name + '}';
    }

    /**
     * 获取集合中具有特定属性值的元素的集合
     *
     * @param oriList   源集合
     * @param cls       元素的类名
     * @param attrName  元素的属性
     * @param attrValue 元素的属性值
     * @param <T>
     * @return
     */
    private <T> List<T> list(List<T> oriList, Class<T> cls, String attrName, String attrValue) {

        List<T> tarList = new ArrayList<>();

        for (T attr : oriList) {

            try {
                Field f = cls.getDeclaredField(attrName);
                f.setAccessible(true);

                if (attrValue.equals(f.get(attr).toString()))
                    tarList.add(attr);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return tarList;
    }

    public static void main(String[] args) {

        Student student = new Student();

        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Student stu = new Student();
            stu.setName("n_" + i);
            studentList.add(stu);
        }

        //获取studentList中name为n_1的所有元素的集合
        List<Student> stuList = student.list(studentList, Student.class, "name", "n_1");

        System.out.println(stuList);
    }
}
