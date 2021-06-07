package com.daoyun.demo.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户登录实体类
 * @author: MaYan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserLogin",description = "")
public class UserLoginDto {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
}
