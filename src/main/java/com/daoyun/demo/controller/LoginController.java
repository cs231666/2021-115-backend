package com.daoyun.demo.controller;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.RegisterDto;
import com.daoyun.demo.pojo.dto.UserLoginDto;
import com.daoyun.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 登录
 * @author: MaYan
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IUserService iUserService;

    /**
     * 密码登录
     * @param userLoginDto
     * @param request
     * @return
     */
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public ReturnInfo login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        try {
            return iUserService.login(userLoginDto.getUsername(), userLoginDto.getPassword(), request);
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    /**
     * 验证码登录
     * @param userLoginDto
     * @param request
     * @return
     */
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/loginbycode")
    public ReturnInfo loginByCode(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        try {
            return iUserService.loginByCode(userLoginDto.getUsername(), userLoginDto.getCode(), request);
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("快速注册")
    @PostMapping("/register")
    public ReturnInfo userRegister(@RequestBody RegisterDto registerDto, HttpServletRequest request) {
        try {
            return this.iUserService.userRegister(registerDto.getUsername(), registerDto.getCode(),registerDto.getRole(), request);
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("密码注册")
    @PostMapping("/registerByPsw")
    public ReturnInfo userRegisterByPsw(@RequestBody RegisterDto registerDto, HttpServletRequest request) {
        try {
            return this.iUserService.userRegisterByPsw(registerDto, request);
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    /**
     * 前端在拿到200后，直接在前端删除token;
     *
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ReturnInfo logout() {
        return ReturnInfo.success("退出成功!");
    }
}
