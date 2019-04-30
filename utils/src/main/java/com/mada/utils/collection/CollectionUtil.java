package com.mada.utils.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Created by madali on 2018/1/3.
 */
public class CollectionUtil {

    /**
     * 判断Collection是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断map是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

}
