package com.daoyun.demo.service;

import com.daoyun.demo.controller.dto.RegisterBean;
import com.daoyun.demo.entity.CommonResult;

public interface UserRegisterService {
    CommonResult userRegister(RegisterBean registerBean);
}
