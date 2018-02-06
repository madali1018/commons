package com.common.util.collection;

import java.util.Collection;

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
        return collection == null || collection.size() == 0;
    }

}
