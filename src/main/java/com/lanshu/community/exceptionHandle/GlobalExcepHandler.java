package com.lanshu.community.exceptionHandle;

import com.alibaba.fastjson.JSON;
import com.lanshu.community.dto.ResultDto;
import com.lanshu.community.exception.CustomerEecption;
import com.lanshu.community.exception.QuestionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class GlobalExcepHandler {

    //抛出QuestionException路由到此，进行处理和跳转
    @ExceptionHandler(value = QuestionException.class)
    public String noQuestion(Exception ex, Model model){
        model.addAttribute("message", ex.getMessage());
        return "/error";
    }

    //抛出 CustomerEecption 异常路由到此，进行处理
    @ExceptionHandler(value = CustomerEecption.class)
    public String CustomerEx(Exception ex, Model model, HttpServletRequest request, HttpServletResponse response) {

        if ("application/json".equals(request.getContentType())) {
            //返回JSON，手写返回JSON
            ResultDto resultDto;
            resultDto = ResultDto.errorOf((CustomerEecption) ex);
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                //resultDto 转 JSON String
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //返回页面
            model.addAttribute("message", ex.getMessage());
            return "/error";
        }
    }
}
