package enumeration;

import com.common.util.enumeration.EnumerationUtil;

/**
 * Created by madali on 2017/4/26.
 */
public class EnumerationUtilDemo {

    public static void main(String[] args) {

        //nameOf方法获取枚举类的对象
        WeekDayEnum weekDayEnum = EnumerationUtil.nameOf(WeekDayEnum.class, "sunday");
        System.out.println(weekDayEnum);

        //valueOf方法获取枚举类的对象
        WeekDayEnum weekDayEnum2 = EnumerationUtil.valueOf(WeekDayEnum.class, 3);
        System.out.println(weekDayEnum2);
        //获取枚举类的对象属性，属性值，对象的描述
        System.out.println(weekDayEnum2.name() + "," + weekDayEnum2.value() + "," + weekDayEnum2.description());
    }
}
