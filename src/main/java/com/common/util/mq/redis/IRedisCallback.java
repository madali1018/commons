package com.common.util.mq.redis;

import com.common.util.mq.ICallback;

/**
 * Created by madl on 2017/4/27.
 */
//异步回到消费或订阅
public interface IRedisCallback extends ICallback {

    public void execute(String message);
}
