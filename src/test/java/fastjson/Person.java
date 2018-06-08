package fastjson;

import java.io.Serializable;

/**
 * @Auther: madali
 * @Date: 2018/6/8 18:20
 */
public class Person implements Serializable {

    private int age;
    private String name;

    private static final long serialVersionUID = 1L;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("age=").append(age);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}