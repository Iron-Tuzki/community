package com.lanshu.community.controller;

import com.lanshu.community.dto.PaginationDto;
import com.lanshu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    /*** 欢迎页
     * 首页不拦截
     */
    @GetMapping("/")
    public String index( Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "3") Integer size){
        //调用 service 层 获得问题列表
        PaginationDto paginationDto = questionService.list(page, size);
        //添加到 model 中
        model.addAttribute("paginationDto", paginationDto);
        return "index";
    }
}
