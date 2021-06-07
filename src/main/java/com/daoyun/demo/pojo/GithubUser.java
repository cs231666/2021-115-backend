package com.daoyun.demo.pojo;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class GithubUser {
    private String login;
    private String name;
    private Long id;


//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

}
