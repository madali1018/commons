package com.common.demo.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: madali
 * @Date: 2018/8/20 10:35
 */
public class StreamDemo {

    /**
     * 这种[方法引用]或者说[双冒号运算]对应的参数类型是Function<T,R> T表示传入类型，R表示返回类型。
     * 比如表达式person -> person.getAge(); 传入参数是person，返回值是person.getAge()，那么方法引用Person::getAge就对应着Function<Person,Integer>类型。
     */
    @Test
    public void convertTest() {
        List<String> collected = new ArrayList<>();
        collected.add("alpha");
        collected.add("beta");
        collected = collected.stream().map(string -> string.toUpperCase()).collect(Collectors.toList());
        // 可以替换成下面的写法：
        collected = collected.stream().map(String::toUpperCase).collect(Collectors.toList());

        System.out.println(collected);
    }

    //可变长度的参数只能有一个，且必须是方法的最后一个参数
    public void a(String s1, String... s2) {

    }

}
