package com.daoyun.demo.util;

import cn.hutool.core.util.RandomUtil;

import com.daoyun.demo.pojo.SmsLengthEnum;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsCodeUtil {

    /**
     * 随机生成指定长度的短信的验证码
     *
     * @param smsLengthEnum 短信验证码长度枚举
     * @return 随机验证码
     * @author RenShiWei
     * Date: 2020/9/16 10:53
     */
    public static String createSmsRandomCode(SmsLengthEnum smsLengthEnum) {
        return RandomUtil.randomNumbers(smsLengthEnum.getLength());
    }

    /**
     * 创建短信验证码，缓存键策略
     * 策略：前缀_业务名_手机号
     *
     * @param prefix      前缀
     * @param phone       手机号
     * @param businessStr 业务名
     * @return 短信验证码，缓存键策略
     * @author RenShiWei
     * Date: 2020/9/16 10:53
     */
    public static String createSmsCacheKey(String prefix, String phone, String businessStr) {
        return prefix + "_" + businessStr + "_" + phone;
    }

}
