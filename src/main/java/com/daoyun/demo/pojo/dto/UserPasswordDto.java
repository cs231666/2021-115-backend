package com.daoyun.demo.pojo.dto;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class UserPasswordDto {
    String username;
    String oldpassword;
    String newpassword;
    String code;
}
