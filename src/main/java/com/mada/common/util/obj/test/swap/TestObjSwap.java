package com.mada.common.util.obj.test.swap;

import com.mada.common.util.obj.ObjUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: madali
 * @Date: 2018/8/20 10:40
 */
public class TestObjSwap {

    static A a;

    static {
        List<String> list = new ArrayList<>();
        list.add("str1");
        list.add("str2");

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "v1");
        map.put(2, "v2");

        a = new A(111, 1, true, "a", new int[]{1, 2, 3}, list, map, new User("username", "password"));
    }

    @Test
    public void test1() {

        B b = new B();

        System.out.println("对象覆盖前a:" + a);
        System.out.println("对象覆盖前b:" + b);

        ObjUtil.swap(a, b);

        a.setAge(111111111);
        a.getList().add("stra");
        a.getMap().put(3, "v3");
        a.getUser().setUsername("unamea");

        int[] arrA = new int[]{1, 2, 3, 4};
        int[] arrB = new int[]{1, 2, 3, 5};
        a.setArray(arrA);
        b.setArray(arrB);

        b.setName("bbbbbbbb");
        b.getList().add("strb");
        b.getMap().put(4, "v4");
        b.getUser().setUsername("unameb");

        System.out.println("对象覆盖后a:" + a);
        System.out.println("对象覆盖后b:" + b);
    }

    @Test
    public void test2() throws Exception {

        Integer frequency = 100000;//转换次数
        List list = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < frequency; i++) {
            B b = new B();
            ObjUtil.swap(a, b);
            list.add(b);
        }

        System.out.println("转换" + frequency + "条数据，耗时：" + (System.currentTimeMillis() - start) + "ms.");
    }

    @Test
    public void test3() {
        Object obj = new A();
        Map<String, Object[]> fieldAnnotationMap = ObjUtil.getFieldAnnotationMap(obj);
        System.out.println(fieldAnnotationMap.size());
    }

}
