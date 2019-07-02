package com.mada.commons.demo.concurrent.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * Created by madali on 2019/7/1 15:11
 */
public class ForkJoinTest3 {

    public static void main(String[] args) {
        Instant start = Instant.now();
        // 并行流
        LongStream.rangeClosed(0, 110).parallel().reduce(0, Long::sum);

        // 顺序流
        LongStream.rangeClosed(0, 110).sequential().reduce(0, Long::sum);

        Instant end = Instant.now();
        System.out.println("耗时" + Duration.between(start, end).toMillis());
    }

}
