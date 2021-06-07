package com.daoyun.demo.controller;


import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.ISmsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"sms"})
public class SmsController {
    @Autowired
    ISmsService iSmsService;

    @ApiOperation("实现发送短信")
    @PostMapping({"{phone}"})
    public ReturnInfo sendSmsCode(@PathVariable("phone") String phone) {
        try{
            iSmsService.sendSmsCode(phone);
            return ReturnInfo.success("发送成功");
        }catch (Exception e){
            return ReturnInfo.error("发送失败");
        }
    }
}
