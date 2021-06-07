package com.daoyun.demo.config.security;

import com.daoyun.demo.pojo.ReturnInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 当未登录或者token失效访问接口时，自定义的返回结果
 * @author: MaYan
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ReturnInfo returnInfo = ReturnInfo.error("未登录，请登录");
        returnInfo.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(returnInfo));
        out.flush();
        out.close();
    }
}
