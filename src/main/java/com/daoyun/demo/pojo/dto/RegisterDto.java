package com.daoyun.demo.pojo.dto;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RegisterDto {
    private String username;
    private String password;
    private int role;
    private String code;
}
