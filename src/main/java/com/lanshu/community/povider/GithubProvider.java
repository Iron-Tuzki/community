package com.lanshu.community.povider;

import com.alibaba.fastjson.JSON;
import com.lanshu.community.dto.AccessTokenDTO;
import com.lanshu.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            /*截取 accesstoken*/
            String accesstoken = response.body().string().split("&")[0].split("=")[1];
            /*打印响应体中的内容，是否有accesstoken*/
            System.out.println("accesstoken = "+accesstoken);
            return accesstoken;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("getUser方法运行");
            /*将响应体中的 String 对象，转换为 GithubUser 类对象*/
            return JSON.parseObject(response.body().string(), GithubUser.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
