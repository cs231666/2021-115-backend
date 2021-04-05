package com.daoyun.demo.service;

import com.daoyun.demo.entity.CommonResult;
import com.daoyun.demo.entity.User;

import java.util.List;

public interface UserLoginService {

    CommonResult userLogin(String username,String password);


}
