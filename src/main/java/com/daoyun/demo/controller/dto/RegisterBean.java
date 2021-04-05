package com.daoyun.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBean {
    private String role;
    private String username;
    private String name;
    private String sno;
    private String password;
//    private String confPassword;
}
