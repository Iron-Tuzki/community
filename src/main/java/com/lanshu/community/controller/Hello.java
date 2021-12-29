package com.lanshu.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {

    @GetMapping("/hello")
    public String Hello(@RequestParam(value = "mingzi") String name, Model model){
        model.addAttribute("userName",name);
        return "hello";
    }
}
