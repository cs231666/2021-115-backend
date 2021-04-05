package com.daoyun.demo.service.impl;

import com.daoyun.demo.dao.UserDao;
import com.daoyun.demo.entity.CommonResult;
import com.daoyun.demo.entity.User;
import com.daoyun.demo.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired(required = false)
    UserDao userDao;



    @Override
    public CommonResult userLogin(String username, String password) {
        if(!(userDao.getUserCount(username)>0)){

            System.out.println(username + ":" + password);
            return new CommonResult(null,"无此用户",410);

        }else{
            User user = userDao.getUserByUsername(username);
            if(user.getPassword().equals(password)){
//                System.out.println(createTKN(username));
                String tkn = createTKN(username);
                user.setToken(tkn);
                return new CommonResult(user,"登录成功",200);
            }else{
                return new CommonResult(null,"密码错误",411);
            }

        }
    }
    public String createTKN(String username){

        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String token = uuid.toString();
        // 转换为大写
        token = token.toUpperCase();
        // 替换 “-”变成空格
        token = token.replaceAll("-", "");
        System.out.println(token);
        String userToken = username + "_" + token;
//        System.out.println(userToken);


        userDao.updateUserToken(username,userToken);
        return userToken;

    }



}
