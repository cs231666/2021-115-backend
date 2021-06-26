package com.daoyun.demo.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private Integer id;

    private Integer role;

    private String username;

    private String password;

    private String sex;

    private String nickname;

    private String realname;

    private String studentId;

    private String phone;

    private String email;

    private String org;

    private String birthday;

    private String token;

    private Integer join;

    private Integer create;
}
