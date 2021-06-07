package com.daoyun.demo.controller;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.RegisterDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 生成redis键值对，模拟验证码用
 * @author: MaYan
 */
@RestController
@RequestMapping("/test")

public class testController {
    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("自定义键值对")
    @PostMapping("/{key}/{value}")
    public void test(@PathVariable("key") String key,@PathVariable("value") String value){
        redisTemplate.opsForValue().set("pinganky_sms_User_sign_"+key, value);
    }

}
