package com.mada.commons.entity.spring;

import com.mada.commons.entity.hibernate.BaseModel;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
