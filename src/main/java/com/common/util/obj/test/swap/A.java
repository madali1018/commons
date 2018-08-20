package com.common.util.obj.test.swap;

import com.common.util.obj.FieldDescriptionAnnotation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2017/5/17.
 */
public class A {

    @FieldDescriptionAnnotation(fieldDescription = "10")
    private int age;

    private int id;

    @FieldDescriptionAnnotation(fieldDescription = "false")
    private Boolean flag;

    @FieldDescriptionAnnotation(fieldDescription = "name")
    private String name;

    @FieldDescriptionAnnotation(fieldDescription = "array")
    private int[] array;

    @FieldDescriptionAnnotation(fieldDescription = "list")
    private List<String> list;

    @FieldDescriptionAnnotation(fieldDescription = "collection/map")
    private Map<Integer, String> map;

    @FieldDescriptionAnnotation(fieldDescription = "user")
    private User user;

    public A() {
    }

    public A(int age, int id, Boolean flag, String name, int[] array, List<String> list, Map<Integer, String> map, User user) {
        this.age = age;
        this.id = id;
        this.flag = flag;
        this.name = name;
        this.array = array;
        this.list = list;
        this.map = map;
        this.user = user;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("A{");
        sb.append("age=").append(age);
        sb.append(", id=").append(id);
        sb.append(", flag=").append(flag);
        sb.append(", name='").append(name).append('\'');
        sb.append(", array=").append(Arrays.toString(array));
        sb.append(", list=").append(list);
        sb.append(", collection.map=").append(map);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString().replace("'null'", "null");
    }
}