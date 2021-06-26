package com.daoyun.demo.componet;

import com.daoyun.demo.mapper.SignInMapper;
import com.daoyun.demo.pojo.SignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncStopSignIn {

    @Autowired(required = false)
    private SignInMapper signInMapper;

    @Async
    public void stopSignIn(SignIn signIn, int second){
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        signIn.setStatus(0);
        signInMapper.updateById(signIn);
    }
}
