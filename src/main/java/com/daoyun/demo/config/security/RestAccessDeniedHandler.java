package com.daoyun.demo.config.security;

import com.daoyun.demo.pojo.ReturnInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 当访问接口没有权限时，自定义返回结果
 * @author: MaYan
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ReturnInfo returnInfo = ReturnInfo.error("权限不足，请联系管理员");
        returnInfo.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(returnInfo));
        out.flush();
        out.close();
    }
}
