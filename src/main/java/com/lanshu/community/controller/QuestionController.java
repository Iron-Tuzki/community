package com.lanshu.community.controller;

import com.lanshu.community.dto.QuestionDto;
import com.lanshu.community.exception.CustomerEecption;
import com.lanshu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 跳转到问题详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Integer id, Model model) throws CustomerEecption {

        QuestionDto questionDto = questionService.findById(id);
        //阅读数加一
        questionService.incViewCount(id, questionDto.getViewCount());
        //展示问题详情
        model.addAttribute("questionDto", questionDto);
        return "question";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Integer id){
        questionService.deleteById(id);
        return "redirect:/profile/myquestions";
    }
}
