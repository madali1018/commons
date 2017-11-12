package obj.swap;

import com.common.util.obj.ObjUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2017/5/17.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Test test = new Test();

        int[] arr = new int[]{1, 2, 3};

        List<String> list = new ArrayList<>();
        list.add("str1");
        list.add("str2");

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "v1");
        map.put(2, "v2");

        A a = new A(111, 1, true, "a", arr, list, map, new User("username", "password"));
        B b = new B();

//        test.test(a);

//        ObjectSwapUtil.swap(a, b);
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

        System.out.println(a);
        System.out.println(b);
    }

    private static void test(A a) throws Exception {

        List bList = new ArrayList<>();

        long start = System.currentTimeMillis();
        System.out.println(start);

        for (int i = 0; i < 100000; i++) {
            B b = new B();
            ObjectSwapUtil.swap(a, b);
            bList.add(b);
        }

        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("转换10万条数据，耗时：" + (end - start) + "ms.");
    }
}
