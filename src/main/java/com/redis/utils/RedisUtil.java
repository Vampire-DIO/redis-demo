package com.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassNameRedisUtil
 * @Description TODO
 * @Author Snow_D
 * Date 2020/12/10 19:16
 * @Version 1.0
 */
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public long bitCount(String key){
        return stringRedisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
    }

}
