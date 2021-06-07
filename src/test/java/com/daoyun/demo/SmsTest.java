package com.daoyun.demo;

import com.daoyun.demo.service.ISmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsTest {
    @Autowired
    private ISmsService smsService;
    //    @Resource
//    TxCloudSmsUtil txCloudSmsUtil;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testSms() {
//        // 生成随机的六位数验证码
//        Random random = new Random(4);
//        Integer verifyCode = random.nextInt(1000000);
//        SmsParams smsParams = new SmsParams("13055662738",verifyCode.toString());
//        System.out.println("生成的验证码为：" + verifyCode);
//        // smsParams.setPhone("17098705205").setVerifyCode();
//        String str = txCloudSmsUtil.sendSms(smsParams);
//        System.out.println(str);
//        smsService.sendSmsCode("13055662738");
        redisTemplate.opsForValue().set("myKey", "haiYang");
//        redisTemplate.opsForValue().get("pinganky_sms_User_sign_13055662738");
//        redisTemplate.opsForValue().set("pinganky_sms_User_sign_13055662738", "1234");
        redisTemplate.opsForValue().set("pinganky_sms_User_sign_15750735837", "1234");
        System.out.println(redisTemplate.opsForValue().get("pinganky_sms_User_sign_13055662738"));
    }

}