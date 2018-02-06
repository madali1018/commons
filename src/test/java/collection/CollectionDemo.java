package collection;

import com.common.util.array.ArrayUtil;

/**
 * Created by madali on 2018/1/11.
 */
public class CollectionDemo {

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 2, 15, 1, 6, 4, 7, 15, 22, 16};
        System.out.println(ArrayUtil.countOccurrences(array, 2));
    }

}
