package com.daoyun.demo.provider;

import com.alibaba.fastjson.JSON;
import com.daoyun.demo.pojo.GithubUser;
import com.daoyun.demo.pojo.dto.AccessTokenDTO;
import org.springframework.stereotype.Component;
import okhttp3.*;
import java.io.IOException;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

            String[] split = string.split("&");
            String token = split[0].split("=")[1];
            return token;
        } catch (IOException e) {
        }
        return null;
    }
    //GET方式获得User
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token="+ accessToken)
//                .build();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//将string解析成GitHub对象
            return githubUser;
        } catch (IOException e) {
            return null;
        }
    }
}

