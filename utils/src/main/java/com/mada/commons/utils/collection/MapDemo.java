package com.mada.commons.utils.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: madali
 * @Date: 2018/8/21 19:51
 */
public class MapDemo {

    private static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "a");
        map.put(2, "b");
        map.put(1, "c");
    }

    public static void main(String[] args) {

        System.out.println(map.get(1));

        System.out.println(Long.MAX_VALUE);
        System.out.println(map.isEmpty());
        System.out.println(map.get(1));
        System.out.println(map.get(10));
        System.out.println(map.getOrDefault(10,"defaultResult"));
    }

}
