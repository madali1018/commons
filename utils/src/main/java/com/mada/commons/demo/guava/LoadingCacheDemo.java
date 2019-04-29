package com.mada.commons.demo.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by madali on 2019/3/19 14:12
 */
@Slf4j
public class LoadingCacheDemo {

    //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
    private static LoadingCache<Integer, Student> studentCache = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置写缓存后8秒钟过期
            .expireAfterWrite(8, TimeUnit.SECONDS)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100)
            //设置要统计缓存的命中率
            .recordStats()
            //设置缓存的移除通知
            .removalListener(notification -> log.info(notification.getKey() + " was removed, cause is " + notification.getCause()))
            //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
            .build(
                    new CacheLoader<Integer, Student>() {
                        @Override
                        public Student load(Integer key) throws Exception {
                            log.info("开始加载缓存数据:{}", key);
                            return Student.builder().id(key).name("name" + key).build();
                        }
                    }
            );

    @Test
    public void t1() {
        for (int i = 0; i < 5; i++) {
            //从缓存中得到数据，由于没有设置过缓存，所以需要通过CacheLoader加载缓存数据
            try {
                log.info("缓存中取到的数据:{}", studentCache.get(i));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //休眠1秒
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //最后打印缓存的命中率等 情况
        log.info(studentCache.stats().toString());
    }

    @Data
    @Builder
    private static class Student {

        private int id;

        private String name;

    }

}

