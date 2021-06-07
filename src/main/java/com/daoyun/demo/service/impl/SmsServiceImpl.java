package com.daoyun.demo.service.impl;

import com.daoyun.demo.pojo.SmsLengthEnum;
import com.daoyun.demo.pojo.SmsProperties;
import com.daoyun.demo.pojo.SmsResponseCodeEnum;
import com.daoyun.demo.pojo.SmsUtil;
import com.daoyun.demo.service.ISmsService;
import com.daoyun.demo.util.RedisUtils;
import com.daoyun.demo.util.SmsCodeUtil;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceImpl implements ISmsService {

    private final SmsProperties smsProperties;

    private final RedisUtils redisUtils;



    /** 腾讯云短信模板id-短信验证码 */
    @Value("${sms-config.templateIds.code}")
    private String templateIdCode;

    /**
     * description: 发送短信验证码
     *
     * @param phone 手机号
     * @author RenShiWei
     * Date: 2020/9/16 10:11
     */
    @Override
    public void sendSmsCode(String phone) {
        // redis缓存的键
        final String smsKey = SmsCodeUtil.createSmsCacheKey(smsProperties.getPhonePrefix(), phone, "User_sign");

        //下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]  示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号
        String[] phoneNumbers = {"+86" + phone};
        //模板参数: 若无模板参数，则设置为空（第一个参数为随机验证码，第二个参数为有效时间）
        final String smsRandomCode = SmsCodeUtil.createSmsRandomCode(SmsLengthEnum.SMS_LENGTH_4);
        String[] templateParams = {smsRandomCode,
                smsProperties.getExpireTime()};

        SendStatus[] sendStatuses = SmsUtil.sendSms(smsProperties, templateIdCode, templateParams, phoneNumbers);
        String resCode = sendStatuses[0].getCode();
        if (resCode.equals(SmsResponseCodeEnum.OK.getCode())) {
            // 返回ok，缓存
            redisUtils.set(smsKey, smsRandomCode, Long.parseLong(smsProperties.getExpireTime()), TimeUnit.MINUTES);
        } else {
            log.error("【短信业务-发送失败】phone:" + phone + "errMsg:" + sendStatuses[0].getMessage());
            // throw new BaseException(ResponseEnum.SMS_NOT_SEND);
        }
    }

    /**
     * description:验证短信验证码
     *
     * @param username 手机号
     * @param code  验证码
     * @author RenShiWei
     * Date: 2020/9/16 10:11
     */
//    @Autowired(required = false)
//    UserService userService;
    @Override
    public int verifyCode(String username, String code) {
        // redis缓存的键
        final String smsKey = SmsCodeUtil.createSmsCacheKey(smsProperties.getPhonePrefix(), username,
                "User_sign");
        // 如果key存在（存在并且未过期）
        if (redisUtils.hasKey(smsKey)) {
            String cacheCode = redisUtils.get(smsKey).toString();
            if (cacheCode.equals(code)) {//验证码正确
                //删除验证码缓存
//                redisUtils.del(smsKey);
                log.info("注册成功phone：" + username);
                //注册成功
                return 0;
            } else {
                //验证码不正确
                log.error("验证码错误。phone：" + username);
                return 1;
//                return new ReturnInfo(SystemStaticConst.FAIL,"验证码错误",null);
            }
        } else {
            //验证码失效
            log.error("验证码失效。phone：" + username);
            return 2;
//            //throw new BaseException(ResponseEnum.SMS_CODE_EXPIRE);
        }

    }

}

