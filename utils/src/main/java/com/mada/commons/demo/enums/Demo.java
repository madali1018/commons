package com.mada.commons.demo.enums;

import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2019/7/2 11:18
 */
public class Demo {

    @Test
    public void t1() {
        EnumMap enummap = new EnumMap(Day.class);

        enummap.put(Day.MONDAY, "work work");
        enummap.put(Day.TUESDAY, "work work");
        enummap.put(Day.WEDNESDAY, "work work");
        enummap.put(Day.THURSDAY, "work work");
        enummap.put(Day.FRIDAY, "work work");
        enummap.put(Day.SATURDAY, "have fun");
        enummap.put(Day.SUNDAY, "have fun");

        System.out.println(enummap);

        String job = (String) enummap.get(Day.FRIDAY);
        System.out.println("job is:" + job);
    }

    @Test
    public void t2() {
        // 1.noneOf创建空队列
        EnumSet enumset = EnumSet.noneOf(Day.class);
        System.out.println("Before:" + enumset);

        enumset.add(Day.MONDAY);
        enumset.add(Day.TUESDAY);
        enumset.add(Day.WEDNESDAY);

        System.out.println("After:" + enumset);

        // 2.allOf创建满队列
        EnumSet enumset2 = EnumSet.allOf(Day.class);
        System.out.println(enumset2);

        // 3.range创建指定范围队列
        EnumSet enumset3 = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        System.out.println(enumset3);

        // 4.complementOf补集创建队列
        EnumSet enumset4 = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        System.out.println("enumset4:" + enumset4);
        EnumSet enumset4_2 = EnumSet.complementOf(enumset4);
        //创建enumset4的补集enumset4_2
        System.out.println("enumset4_2:" + enumset4_2);

        EnumSet enumset5 = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        System.out.println("enumset5:" + enumset5);
        EnumSet enumset5_2 = EnumSet.copyOf(enumset5);
        //复制enumset5创建enumset5_2
        System.out.println("enumset5_2:" + enumset5_2);

        // 6.copyOf复制ArrayList创建
        List<Day> list = Arrays.asList(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.WEDNESDAY);
        System.out.println("list:" + list);

        EnumSet enumset6 = EnumSet.copyOf(list);
        System.out.println("enumset6:" + enumset6);
    }

}
