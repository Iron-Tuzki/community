package com.lanshu.community.controller;

import com.lanshu.community.dto.AccessTokenDTO;
import com.lanshu.community.dto.GithubUser;
import com.lanshu.community.povider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    /*@value 会读取配置文件，存放到map中，并赋值*/
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code ,@RequestParam(value = "state") String state){

        System.out.println("code = "+ code);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);

        /*使用 code 获取 access token*/
        String accesstoken = githubProvider.getAccessToken(accessTokenDTO);
        /*使用 accesstoken 获取 userName*/
        GithubUser githubUser = githubProvider.getUser(accesstoken);
        System.out.println("用户名：" + githubUser.getName());

        return "index";
    }

}