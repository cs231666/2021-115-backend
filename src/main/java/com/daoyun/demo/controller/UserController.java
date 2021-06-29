package com.daoyun.demo.controller;


import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.UserInfo;
import com.daoyun.demo.pojo.dto.RegisterDto;
import com.daoyun.demo.pojo.dto.UserPasswordDto;
import com.daoyun.demo.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MaYan
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @ApiOperation("前端添加用户")
    @PostMapping("/adduser")
    public ReturnInfo addUser(@RequestBody RegisterDto registerDto ){

        try{
            return this.iUserService.addUser(registerDto);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
    @ApiOperation("修改密码")
    @PutMapping("reset-password")
    public ReturnInfo changePassword(@RequestBody UserPasswordDto userPasswordDto) {
        try {
            return this.iUserService.changePassword(userPasswordDto);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("忘记密码")
    @PutMapping("forgotPassword")
    public ReturnInfo forgotPassword(@RequestParam String username, @RequestParam String code){
        try {
            return iUserService.forgotPassword(username, code);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }


    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("/allinfo")
    public ReturnInfo getAllUserInfo() {
        try {
            List<User> user = iUserService.getAllUser();
            return ReturnInfo.success("查询成功",user);
        }catch (Exception e){
            return ReturnInfo.error("查询失败");
        }
    }

    /**
     * Principal拿不到值，换种方法
     * @param
     * @return
     */
//    @ApiOperation(value = "获取当前用户信息")
//    @GetMapping("/info")
//    public ReturnInfo getUserInfo(Principal principal) {
//        System.out.println("principal:" + principal);
//        try {
//            if (null == principal) {
//                return null;
//            }
//            String username = principal.getName();
//            System.out.println("username:"+ username);
//            User user = iUserService.getUserByUserName(username);
//            user.setPassword(null);
//            return ReturnInfo.success("查询成功",user);
//        }catch (Exception e){
//            return  ReturnInfo.error("查询失败");
//        }
//    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/info")
    public ReturnInfo getUserInfo(HttpServletRequest request) {

        try {
           return this.iUserService.getUserInfoByToken(request);
        }catch (Exception e){
            return  ReturnInfo.error("查询失败");
        }
    }

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/update-info")
    public ReturnInfo getUserInfoByToken(@RequestBody UserInfo userInfo, HttpServletRequest request) {

        try {
            return this.iUserService.resetUserInfoByToken(userInfo, request);
        }catch (Exception e){
            return  ReturnInfo.error("更新失败");
        }
    }

    @ApiOperation("通过id查找用户")
    @GetMapping("{id}")
    public  ReturnInfo getUserById(@PathVariable("id") Integer id){
        try {
            return this.iUserService.getUserById(id);
        }catch (Exception e){
            return ReturnInfo.error("查询失败");
        }
    }

    @ApiOperation("方便前端和移动端在测试时通过用户名删除用户")
    @DeleteMapping("/{username}")
    public ReturnInfo deleteUser(@PathVariable("username") String username) {
        return this.iUserService.deleteUser(username);
    }

    @ApiOperation("获取指定角色的用户")
    @SneakyThrows
    @GetMapping("getUsersByRoleId/{role_id}")
    public ReturnInfo getUsersByRoleId(@PathVariable("role_id") Integer role_Id){
        return this.iUserService.getUsersByRoleId(role_Id);

    }
}
