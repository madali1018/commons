package com.mada.commons.demo.cache.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by madali on 2020/6/16 15:27
 */
public class CaffeineDemo {

    @Test
    public void t1() {
        Cache<String, String> cache = Caffeine.newBuilder()
                // 数量上限
                .maximumSize(1024)
                // 过期机制
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // 弱引用key
                .weakKeys()
                // 弱引用value
                .weakValues()
                // 剔除监听
                .removalListener((RemovalListener<String, String>) (key, value, cause) ->
                        System.out.println("key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
                .build();

        // 将数据放入本地缓存中
        cache.put("username", "afei");
        cache.put("password", "123456");

        // 从本地缓存中取出数据
        System.out.println(cache.getIfPresent("username"));
        System.out.println(cache.getIfPresent("password"));

        System.out.println(cache.get("blog", key -> {
            // 本地缓存没有的话，从数据库或者Redis中获取
            return "本地缓存没有的话，从数据库或者Redis中获取";
//            return getValue(key);
        }));
    }

    // 异步加载机制
    @Test
    public void t2() throws ExecutionException, InterruptedException, TimeoutException {
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                // 数量上限
                .maximumSize(2)
                // 失效时间
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                // 异步加载机制
                .buildAsync(key -> {
                    // 本地缓存没有的话，从数据库或者Redis中获取
                    return "本地缓存没有的话，从数据库或者Redis中获取";
//            return getValue(key);
                });
        System.out.println(cache.get("username").get());
        System.out.println(cache.get("password").get(10, TimeUnit.MINUTES));
        System.out.println(cache.get("username").get(10, TimeUnit.MINUTES));
        System.out.println(cache.get("blog").get());
    }

}
