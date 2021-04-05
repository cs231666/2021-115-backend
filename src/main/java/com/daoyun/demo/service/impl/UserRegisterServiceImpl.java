package com.daoyun.demo.service.impl;

import com.daoyun.demo.controller.dto.RegisterBean;
import com.daoyun.demo.dao.UserDao;
import com.daoyun.demo.entity.CommonResult;
import com.daoyun.demo.entity.User;
import com.daoyun.demo.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired(required = false)
    UserDao userDao;
    @Override
    public CommonResult userRegister(RegisterBean registerBean) {
        User user = userDao.getUserByUsername(registerBean.getUsername());
        String msg;
        int code;
        if (user != null){
            msg = "用户存在";
            code = 201;
        }else {
            userDao.AddUser(registerBean.getUsername(), registerBean.getPassword());
            msg = "注册成功";
            code = 200;
        }
        return new CommonResult(null, msg, code);
    }
}
