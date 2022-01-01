package com.lanshu.community.controller;

import com.lanshu.community.dto.PaginationDto;
import com.lanshu.community.dto.QuestionDto;
import com.lanshu.community.mapper.UserMapper;
import com.lanshu.community.model.User;
import com.lanshu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    /*** 欢迎页
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "2") Integer size){
        System.out.println("index display");
        //访问首页时查看cookie
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie: cookies) {
                //如果cookie中有key为token的value，取出并去mysql中查找用户信息
                //如果有匹配用户则放入session中，实现持久化登录状态
                if ("token".equals(cookie.getName())) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null){
                        //用户信息放入session
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        //调用 service 层 获得问题列表
        PaginationDto paginationDto = questionService.list(page, size);
        model.addAttribute("paginationDto", paginationDto);
        return "index";
    }
}
