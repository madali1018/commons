package com.mada.utils.dslock.service;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Created by madali on 2019/10/24 14:26
 */
public class DsLockFactory {

    public static RedissonClient createRedisson(String host, String port, String password) {
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s", host, port)).setPassword(password);
        return Redisson.create(config);
    }

}
