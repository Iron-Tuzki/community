package com.lanshu.community.exceptionHandle;

import com.lanshu.community.exception.QuesEx;
import com.lanshu.community.exception.QuestionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExcepHandler {

    //抛出QuestionException路由到此，进行处理和跳转
    @ExceptionHandler(value = QuestionException.class)
    public String noQuestion(Exception ex, Model model){
        model.addAttribute("message", ex.getMessage());
        return "/error";
    }
    @ExceptionHandler(value = QuesEx.class)
    public String noQues(Exception ex, Model model){
        model.addAttribute("message", ex.getMessage());
        return "/error";
    }


}
