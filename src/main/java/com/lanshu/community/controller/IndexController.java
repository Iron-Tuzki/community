package com.lanshu.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    /**
     * 欢迎页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
