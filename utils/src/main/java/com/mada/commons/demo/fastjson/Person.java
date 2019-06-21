package com.mada.commons.demo.fastjson;

import lombok.*;

/**
 * @Auther: madali
 * @Date: 2018/6/8 18:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private int id;

    private int age;

    private String name;

    private String address;

}