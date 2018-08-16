package obj.deepclone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2017/5/17.
 */
public class Test {

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

    public static void main(String[] args) throws Exception {

        Test test = new Test();
        test.testDeepCloneCglib();

        long start = System.currentTimeMillis();
        System.out.println(start);

        CloneObject obj2 = new CloneObject();
        List<CloneObject> cloneObjectList = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            cloneObjectList.add((CloneObject) obj1.clone());
            cloneObjectList.add((CloneObject) ObjectDeepCloneUtil.deepCloneIOStream(obj1));
            cloneObjectList.add((CloneObject) ObjectDeepCloneUtil.deepCloneFastJson(obj1));
            cloneObjectList.add((CloneObject) ObjectDeepCloneUtil.deepCloneCglib(obj1, obj2));
        }

        long end = System.currentTimeMillis();
        System.out.println(end);

        System.out.println("深拷贝10万条数据，耗时" + (end - start) + "ms.");
    }

    //测试cglib深拷贝
    private static void testDeepCloneCglib() throws Exception {

//        CloneObject obj2 = new CloneObject();
//        obj2 = (CloneObject) ObjectDeepCloneUtil.deepCloneCglib(obj1, obj2);

        CloneObject obj2 = ObjectDeepCloneUtil.deepCloneCglib(obj1);

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
