package com.mada.commons.utils.obj.demo;

import com.mada.commons.utils.obj.utils.ObjSwapUtil;
import com.mada.commons.utils.obj.entity.Coordinates;
import com.mada.commons.utils.obj.entity.A;
import com.mada.commons.utils.obj.entity.B;
import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2019/4/30 10:31
 */
public class ObjectSwapDemo {

    private static A a = A.builder()
            .age(18)
            .id(1)
            .flag(false)
            .name("十八")
            .array(new int[]{1, 2, 3})
            .coordinates(Coordinates.builder().lon(117.2994).lat(31.745151).build())
            .build();

    static {
        List<String> list = new ArrayList<>();
        list.add("str1");
        list.add("str2");

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "v1");
        map.put(2, "v2");

        a.setList(list);
        a.setMap(map);
    }

    @Test
    public void t1() {
        Object obj = new A();
        Map<String, Object[]> fieldAnnotationMap = ObjSwapUtil.getFieldAnnotationMap(obj);
        fieldAnnotationMap.forEach((key, value) -> System.out.println(key + "," + Arrays.toString(value)));
    }

    @Test
    public void t2() {
        B b = new B();
        System.out.println("对象覆盖前a:" + a);
        System.out.println("对象覆盖前b:" + b);

        ObjSwapUtil.swap(a, b);

        b.setName("二十");
        a.getList().add("str3");

        System.out.println("对象覆盖后a:" + a);
        System.out.println("对象覆盖后b:" + b);
    }

}
