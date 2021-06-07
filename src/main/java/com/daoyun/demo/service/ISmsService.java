package com.daoyun.demo.service;

public interface ISmsService {

    /**
     * description: 发送短信验证码
     *
     * @param phone 手机号
     * @author RenShiWei
     * Date: 2020/9/16 10:11
     */
    void sendSmsCode(String phone);

    /**
     * description:验证短信验证码
     *
     * @param username 手机号
     * @param code  验证码
     * @author RenShiWei
     * Date: 2020/9/16 10:11
     */
    int verifyCode(String username, String code);

}
