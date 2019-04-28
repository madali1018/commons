package com.mada.mq.redis;

import com.mada.mq.services.ICallback;

/**
 * Created by madali on 2017/4/27.
 */
//异步回到消费或订阅
public interface IRedisCallback extends ICallback {

    void execute(String message);
}
