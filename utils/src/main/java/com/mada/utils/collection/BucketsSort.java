package com.mada.utils.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 基数排序：https://mp.weixin.qq.com/s/Z8gU9QLpMnA-zoMc9ZeR2w
 * <p>
 * Created by madali on 2019/7/31 10:31
 */
public class BucketsSort {

    private static final int BUCKET_NUM = 10;

    /**
     * 基排序
     *
     * @param arr 待排序数组
     * @return 排好序由小到大的数组
     */
    public static List<Integer> radixSort(List<Integer> arr) {
        // 定义10个桶
        List<List<Integer>> buckets = new ArrayList<>(BUCKET_NUM);
        int radix = 0;

        int maxNumOfRadix;
        while (true) {
            maxNumOfRadix = getPowerNum(radix + 1);
            // 初始化10个数组
            buckets.clear();
            IntStream.range(0, BUCKET_NUM).<List<Integer>>mapToObj(i -> new ArrayList<>()).forEach(buckets::add);

            // 判断当前基是不是最大的
            boolean bucketEnd = true;
            // 第一步，遍历元素，放入对应的桶
            for (Integer item : arr) {
                // 计算元素属于哪个桶
                int radixNum = getRadixNum(item, radix);
                // 放入桶
                buckets.get(radixNum).add(item);

                if (item >= maxNumOfRadix) {
                    bucketEnd = false;
                }
            }

            // 第二步，遍历桶，放回数组，先入桶的先出桶。
            arr = new ArrayList<>();

            for (List<Integer> bucket : buckets) {
                arr.addAll(bucket);
            }

            if (bucketEnd) {
                break;
            } else {
                radix++;
            }
        }

        return arr;
    }

    /**
     * 获取对应基的数字
     *
     * @param num   原始数字
     * @param radix 基：元素的个位、十位、百位，从 0 开始
     * @return 元素对应基的数字，比如 17 的基是 0 的数字是 7
     */
    private static int getRadixNum(int num, int radix) {
        return num / getPowerNum(radix) % 10;
    }

    /**
     * 获取10的幂
     *
     * @param power 10的n次方，从0开始
     * @return 10的power次方
     */
    private static int getPowerNum(int power) {
        return IntStream.rangeClosed(1, power).map(i -> 10).reduce(1, (a, b) -> a * b);
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(72, 11, 82, 32, 44, 13, 17, 95, 54, 28, 79, 56);
        System.out.println("排序前:" + list);

        List<Integer> list2 = radixSort(list);
        System.out.println("排序后:" + list2);
    }

}
