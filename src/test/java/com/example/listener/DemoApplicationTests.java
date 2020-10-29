package com.example.listener;

import com.example.util.NumberUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private SmsListener smsListener;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 发送
     */
    @Test
    void sendSms() {
        //生成验证码
        String code = NumberUtils.generateCode(6);
        //发送消息到rabbitMQ
        Map<String, String> map = new HashMap<>();
        map.put("phone", "17333255285");
        map.put("code", "{code:"+code+"}");
        smsListener.sendSms(map);
        //验证码保存redis,5分钟
        stringRedisTemplate.opsForValue().set(map.get("phone"), code, 5, TimeUnit.MINUTES);
    }

    /**
     * 获取code
     */
    @Test
    void getCode(){
        String s = stringRedisTemplate.opsForValue().get("17333255285");
        System.out.println(s);
    }

}
