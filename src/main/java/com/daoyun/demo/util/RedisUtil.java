package com.daoyun.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *
     * @param key  Redis缓存key值
     * @param timeout 设置缓存失效的分钟值
     * @return
     */
    public long incr(String key,int timeout){
        Long id = redisTemplate.opsForValue().increment(key);
        if(timeout>0){
            redisTemplate.expire(key,timeout, TimeUnit.MINUTES);
        }
        return id;
    }
}

