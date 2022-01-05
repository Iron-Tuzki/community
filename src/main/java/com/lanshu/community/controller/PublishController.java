package com.lanshu.community.controller;

import com.lanshu.community.dto.QuestionDto;
import com.lanshu.community.exception.QuesEx;
import com.lanshu.community.model.Question;
import com.lanshu.community.model.User;
import com.lanshu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(String title, String description, String tag,
                            @RequestParam(name = "id", required = false) Integer id,
                            HttpServletRequest request, Model model) {

        //用于有空内容时进行回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //内容不能为空，
        if (title == null || title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if (description == null || description.equals("")){
            model.addAttribute("error","内容不能为空");
            return "/publish";
        }
        if (tag == null || tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        //三项内容全不为空，则验证用户信息
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            //用户未登录，返回问题编辑页面，并显示用户未登录信息
            return "/publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        //如果是第一次创建问题，则id=null，在creatOrUpdate方法中通过id判断是创建还是更新问题，并获得该问题的ID
        question.setId(id);
        Integer urlId = questionService.creatOrUpdate(question);
        //发布成功，返回该问题详情页
        return "redirect:/question/" + urlId;
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id, Model model) throws QuesEx {
        QuestionDto questionDto = questionService.findById(id);
        model.addAttribute("title", questionDto.getTitle());
        model.addAttribute("description", questionDto.getDescription());
        model.addAttribute("tag", questionDto.getTag());
        model.addAttribute("id", questionDto.getId());
        return "/publish";
    }
}
