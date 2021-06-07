package com.daoyun.demo.controller;


import com.daoyun.demo.mapper.UserMapper;
import com.daoyun.demo.pojo.GithubUser;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.dto.AccessTokenDTO;
import com.daoyun.demo.provider.GitHubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RestController
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;



//    @GetMapping("/callback")
//    public String callback(@RequestParam(name="code") String code,
//                         @RequestParam(name = "state") String state, HttpServletResponse response) throws IOException {
//        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//        accessTokenDTO.setClient_id("c31033a905eac469ce3b");
//        accessTokenDTO.setClient_secret("8253dea7e6f3c7287a659995c0a907ca58a6fb4a");
//        accessTokenDTO.setCode(code);
//        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
//        accessTokenDTO.setState(state);
//        //进行doPost请求，获取access_token
//        //gitHubProvider.getAccessToken(accessTokenDTO);
//        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);//进行doPost请求，获取access_token
//        System.out.println("accessToken:"+ accessToken);
//        GithubUser githubUser = gitHubProvider.getUser(accessToken);//使用accesstok获得user信息
////        System.out.println(githubUser.toString());
//        log.info(githubUser.toString());
//        if(githubUser.getLogin()!= null){
//            this.userDao.AddUser(githubUser.getLogin(), "123456", 0);
//        }
//        response.setHeader("Authorization",accessToken);
////        response.sendRedirect("http://localhost:8080/#/dashboard");
//        return "index";
//    }
    @GetMapping("/callback")
    public void callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state, HttpServletResponse response) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("c31033a905eac469ce3b");
        accessTokenDTO.setClient_secret("8253dea7e6f3c7287a659995c0a907ca58a6fb4a");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://106.15.176.97:8080/callback");
        accessTokenDTO.setState(state);
        //进行doPost请求，获取access_token
        //gitHubProvider.getAccessToken(accessTokenDTO);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);//进行doPost请求，获取access_token
        System.out.println("accessToken:"+ accessToken);
        GithubUser githubUser = gitHubProvider.getUser(accessToken);//使用accesstok获得user信息
//        System.out.println(githubUser.toString());
        log.info(githubUser.toString());
        if(githubUser.getLogin()!= null){
            User user = new User();
            user.setUsername(githubUser.getLogin());
            user.setPassword("123456");
            user.setRole(0);
            user.setCreationDate(LocalDateTime.now());
            user.setCreator(githubUser.getLogin());
            user.setToken(accessToken);
            this.userMapper.insert(user);
        }
        response.setHeader("Authorization",accessToken);
        response.sendRedirect("http://localhost:8080/#/dashboard");
//        return "index";
    }
}
