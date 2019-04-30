package com.mada.commons.utils.enumeration;

import com.mada.commons.enumeration.WeekDayEnum;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by madali on 2017/4/26.
 */
public class EnumerationDemo {

    @Test
    public void test1() {
        //nameOf方法获取枚举类的对象
        WeekDayEnum weekDayEnum = EnumerationUtil.nameOf(WeekDayEnum.class, "sunday");
        System.out.println(weekDayEnum);

        //valueOf方法获取枚举类的对象
        WeekDayEnum weekDayEnum2 = EnumerationUtil.valueOf(WeekDayEnum.class, 3);
        System.out.println(weekDayEnum2);
        //获取枚举类的对象属性，属性值，对象的描述
        System.out.println(weekDayEnum2.name() + "," + weekDayEnum2.value() + "," + weekDayEnum2.description());
    }

    // commons-lang3包下的EnumUtils
    @Test
    public void test2() {
        WeekDayEnum weekDayEnum = EnumUtils.getEnum(WeekDayEnum.class, "Sunday");
        System.out.println(weekDayEnum);
        WeekDayEnum weekDayEnum2 = EnumUtils.getEnumIgnoreCase(WeekDayEnum.class, "sunday");
        System.out.println(weekDayEnum2);

        System.out.println(EnumUtils.isValidEnum(WeekDayEnum.class, "Sunday"));
        System.out.println(EnumUtils.isValidEnumIgnoreCase(WeekDayEnum.class, "sunday"));
        System.out.println("-------------");

        List<WeekDayEnum> list = EnumUtils.getEnumList(WeekDayEnum.class);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("-------------");

        Map<String, WeekDayEnum> map = EnumUtils.getEnumMap(WeekDayEnum.class);
        Set<Map.Entry<String, WeekDayEnum>> set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
