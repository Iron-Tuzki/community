package com.lanshu.community.controller;

import com.lanshu.community.dto.AccessTokenDTO;
import com.lanshu.community.dto.GithubUser;
import com.lanshu.community.mapper.UserMapper;
import com.lanshu.community.model.User;
import com.lanshu.community.povider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

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

        //登录成功，用户信息写入数据库中
        if (githubUser != null && githubUser.getId()!=0){
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));  // int 转 String
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatar_url());
            if (user.getName()!=null){
                userMapper.insert(user);
                //token 写入 cookie
                response.addCookie(new Cookie("token",token));
                //request.getSession().setAttribute("user",user);
            }
            //重定向回首页 index
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}