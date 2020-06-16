package com.mada.commons.demo.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * Created by madali on 2019/3/19 14:12
 */
@Log4j2
public class LoadingCacheDemo {

    private static LoadingCache<Integer, CacheEntity> cache = CacheBuilder.newBuilder()
            // 设置写缓存两分钟后过期
            .expireAfterWrite(Duration.ofMinutes(2))
            //设置缓存容器的初始容量为2
            .initialCapacity(2)
            //设置缓存最大容量为16，超过16之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(16)
            .build(new CacheLoader<Integer, CacheEntity>() {
                @Override
                public CacheEntity load(Integer i) throws Exception {
                    return CacheEntity.builder().id(i).name("name" + i).build();
                }
            });

    @Test
    public void t1() {
        for (int i = 0; i < 20; i++) {
            try {
                log.info("缓存中取到的数据:{}", cache.get(i));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Data
    @Builder
    private static class CacheEntity {
        private int id;
        private String name;
    }

}

