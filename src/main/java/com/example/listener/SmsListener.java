package com.example.listener;

import com.aliyuncs.CommonResponse;
import com.example.util.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Description:
 * @Author: jqq
 * @Date: 2020/10/29 14:23
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "sms.queue", durable = "true"),
            exchange = @Exchange(value = "sms.exchange"),
            key = {"code_sms"}))
    public void sendSms(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        String phone = map.get("phone");
        String code = map.get("code");
        if (StringUtils.isNotEmpty(phone) && StringUtils.isNotEmpty(code)) {
            CommonResponse response = smsUtil.sendSms(phone, code);
        }

    }
}
