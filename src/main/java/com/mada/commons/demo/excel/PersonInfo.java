package com.mada.commons.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * Created by madali on 2018/12/10 11:00
 */
public class PersonInfo extends BaseRowModel {

    // 通过 @ExcelProperty 注解与 index 变量可以标注成员变量所映射的列作为Excel的模型对象,需要setter方法
    @ExcelProperty(index = 0)
    private String name;
    @ExcelProperty(index = 1)
    private Integer age;
    @ExcelProperty(index = 2)
    private String email;

    public PersonInfo() {
    }

    public PersonInfo(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
