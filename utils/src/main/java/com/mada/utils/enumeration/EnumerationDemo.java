package com.mada.utils.enumeration;

import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2017/4/26.
 */
public class EnumerationDemo {

    @Test
    public void t1() {
        //nameOf方法获取枚举类的对象
        WeekDayEnum weekDayEnum = EnumerationUtil.nameOf(WeekDayEnum.class, "sunday");
        System.out.println(weekDayEnum);

        //valueOf方法获取枚举类的对象
        WeekDayEnum weekDayEnum2 = EnumerationUtil.valueOf(WeekDayEnum.class, 4);
        System.out.println(weekDayEnum2);

        //valueOf方法获取枚举类的对象
        ThreadLevelEnum threadLevelEnum = EnumerationUtil.valueOf(ThreadLevelEnum.class, "1");
        System.out.println(threadLevelEnum);
    }

    // commons-lang3包下的EnumUtils
    @Test
    public void t2() {
        WeekDayEnum weekDayEnum = EnumUtils.getEnum(WeekDayEnum.class, "Sunday");
        System.out.println(weekDayEnum);
        WeekDayEnum weekDayEnum2 = EnumUtils.getEnumIgnoreCase(WeekDayEnum.class, "sunday");
        System.out.println(weekDayEnum2);

        System.out.println(EnumUtils.isValidEnum(WeekDayEnum.class, "Sunday"));
        System.out.println(EnumUtils.isValidEnumIgnoreCase(WeekDayEnum.class, "sunday"));
        System.out.println("-------------");

        List<WeekDayEnum> list = EnumUtils.getEnumList(WeekDayEnum.class);
        list.forEach(System.out::println);
        System.out.println("-------------");

        Map<String, WeekDayEnum> map = EnumUtils.getEnumMap(WeekDayEnum.class);
        Set<Map.Entry<String, WeekDayEnum>> set = map.entrySet();
        set.forEach(System.out::println);
    }
}
