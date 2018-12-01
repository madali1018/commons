package com.mada.commons.util.obj.test.clone;

import com.mada.commons.util.obj.ObjUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: madali
 * @Date: 2018/8/20 10:53
 */
public class TestObjClone {

    static int[] array = {1, 2, 3};
    static List<String> list = new ArrayList<>();
    static Map<Integer, String> map = new HashMap<>();
    static User user = new User("username", "password");
    static CloneObject obj1;

    static {
        list.add("s1");
        list.add("s2");

        map.put(1, "v1");
        map.put(2, "v2");
        map.put(3, "v3");

        obj1 = new CloneObject(10, false, "CloneObject1", array, list, map, user);
    }

    @Test
    public void test1() throws CloneNotSupportedException {

        long start = System.currentTimeMillis();
        List<CloneObject> list = new ArrayList<>();

        Integer frequency = 100000;//转换次数
        for (int i = 0; i < frequency; i++) {
//            list.add((CloneObject) obj1.clone());
//            list.add((CloneObject) ObjUtil.deepCloneFastJson(obj1));
//            list.add((CloneObject) ObjUtil.deepCloneIOStream(obj1));
            list.add(ObjUtil.deepCloneCglib(obj1));
        }

        System.out.println("深拷贝转换" + frequency + "条数据，耗时：" + (System.currentTimeMillis() - start) + "ms.");
    }

    //测试cglib深拷贝
    @Test
    public void testDeepCloneCglib() throws Exception {

        CloneObject obj2 = ObjUtil.deepCloneCglib(obj1);

        System.out.println("原obj1: " + obj1);
        System.out.println("原cligb拷贝出的obj2: " + obj2);

        obj1.setAge(11);
        obj1.setFlag(true);
        obj1.setDescription("aaaaaa");
        obj1.getUser().setUsername("u1");

        int[] array = {1, 2, 3, 4};
        obj2.setArray(array);

        obj2.getMap().put(4, "v4");
        obj2.getList().add("str3");
        obj2.getUser().setUsername("u2");

        System.out.println("重新赋值后obj1: " + obj1);
        System.out.println("重新赋值cligb拷贝出的b后: " + obj2);
    }

}
