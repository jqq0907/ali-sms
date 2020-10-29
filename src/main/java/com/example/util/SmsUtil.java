package com.example.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 工具类
 * @Author: jqq
 * @Date: 2020/10/29 14:10
 */
@Component
public class SmsUtil {
    @Value(value = "${sms.accessKeyId}")
    private String accessKeyId;
    @Value(value = "${sms.accessKeySecret}")
    private String accessKeySecret;
    @Value(value = "${sms.signName}")
    private String signName;
    @Value(value = "${sms.codeTemplate}")
    private String codeTemplate;

    /**
     * 发送短信
     *
     * @param phone 手机号码
     * @param code  验证码
     */
    public CommonResponse sendSms(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求对象
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        //短信API产品域名（接口地址固定，无需修改）
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //必填:短信签名
        request.putQueryParameter("SignName", signName);
        //必填:短信模板
        request.putQueryParameter("TemplateCode", codeTemplate);
        //可选:模板中的变量替换JSON串
        request.putQueryParameter("TemplateParam", code);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
            return null;
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
