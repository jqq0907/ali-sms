package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: redis
 * @Author: jqq
 * @Date: 2020/10/29 15:51
 */
@SpringBootTest
public class DemoRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 操作string
     */
    @Test
    void testString() {
        //存储数据
//        stringRedisTemplate.opsForValue().set("key", "value");
        //设置过期时间,2分钟
        stringRedisTemplate.opsForValue().set("key", "value", 2, TimeUnit.MINUTES);
        //获取数据
        String key = stringRedisTemplate.opsForValue().get("key");
        System.out.println(key);
    }

    /**
     * 操作hash
     */
    @Test
    void testHash() {
        //用boundHashOps
        BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps("user");
        //存储数据
        hashOps.put("name", "jsk");
        //获取数据
        hashOps.get("name");
        //获取所有数据
        Map<Object, Object> map = hashOps.entries();
    }
}
