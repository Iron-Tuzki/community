package com.lanshu.community.controller;

import com.lanshu.community.dto.AccessTokenDTO;
import com.lanshu.community.dto.GithubUser;
import com.lanshu.community.povider.GithubProvider;
import com.lanshu.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    /* @Value 会读取配置文件，存放到map中，并在特定时间赋值*/
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response){

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

        if (githubUser != null){
            //获取到 github 用户信息，将其写入数据库中，并生成一个token放入cookie中
            String token = userService.CreatOrUpdate(githubUser);
            response.addCookie(new Cookie("token", token));
            //重定向回首页 index
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //删除session user
        request.getSession().removeAttribute("user");
        //删除cookie token
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}