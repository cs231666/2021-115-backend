package com.daoyun.demo.controller;

import com.daoyun.demo.controller.dto.RegisterBean;
import com.daoyun.demo.entity.CommonResult;
import com.daoyun.demo.entity.User;
import com.daoyun.demo.service.UserLoginService;
import com.daoyun.demo.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired(required = false)
    UserLoginService userLoginService;
    @Autowired(required = false)
    UserRegisterService userRegisterService;

    @GetMapping(value = "userLogin")
    @ResponseBody
    public CommonResult userLogin(String username, String password, HttpServletResponse httpServletResponse) {
        CommonResult commonResult = userLoginService.userLogin(username, password);
        User user = (User) commonResult.data;
        httpServletResponse.setHeader("Authorization", user.getToken());
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return commonResult;
    }

    @PostMapping("userRegister")
    @ResponseBody
    public CommonResult userRegister(@RequestBody RegisterBean registerBean){
        CommonResult commonResult = userRegisterService.userRegister(registerBean);
        return commonResult;
    }
}
