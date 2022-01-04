package com.lanshu.community.controller;

import com.lanshu.community.dto.PaginationDto;
import com.lanshu.community.model.User;
import com.lanshu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class profileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    //@PathVariable 注解用于接收url占位符的值
    public String profile(@PathVariable(name = "action") String action, Model model, HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "3") Integer size){

        //从 session 中获取用户信息，没有则返回首页
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/";

        //根据url占位符中不同数据，在 model 中设置不同的 section sectionName，用于前端展示
        if ("myquestions".equals(action)){
            model.addAttribute("section", "myquestions");
            model.addAttribute("sectionName", "我的提问");

            //调用 service 层，根据user.id获取问题列表
            PaginationDto paginationDto = questionService.list(user.getId(), page, size);
            //添加到 model 中，用于前端展示
            model.addAttribute("paginationDto", paginationDto);

        } else if ("response".equals(action)){
            model.addAttribute("section", "response");
            model.addAttribute("sectionName", "回复我的");
        }

        return "profile";
    }
}
