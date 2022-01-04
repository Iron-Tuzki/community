package com.lanshu.community.controller;

import com.lanshu.community.dto.QuestionDto;
import com.lanshu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Integer id, Model model){

        QuestionDto questionDto = questionService.getQuesById(id);
        model.addAttribute("questionDto", questionDto);
        return "question";
    }
}
