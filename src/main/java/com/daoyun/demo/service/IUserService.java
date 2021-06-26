package com.daoyun.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.UserInfo;
import com.daoyun.demo.pojo.dto.RegisterDto;
import com.daoyun.demo.pojo.dto.UserPasswordDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-06
 */
public interface IUserService extends IService<User> {

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    ReturnInfo login(String username, String password, HttpServletRequest request);

    User getUserByUserName(String username);

    List<User> getAllUser();

    ReturnInfo loginByCode(String username, String code, HttpServletRequest request);

    ReturnInfo userRegister(String username, String code,Integer role, HttpServletRequest request);

    ReturnInfo userRegisterByPsw(RegisterDto registerDto, HttpServletRequest request);

    ReturnInfo addUser(RegisterDto registerDto);

    ReturnInfo changePassword(UserPasswordDto userPasswordDto);

    ReturnInfo getUserById(Integer id);

    ReturnInfo deleteUser(String username);

    ReturnInfo getUserInfoByToken(HttpServletRequest request);

    ReturnInfo resetUserInfoByToken(UserInfo userInfo, HttpServletRequest request);

    ReturnInfo forgotPassword(String username, String code);
}
