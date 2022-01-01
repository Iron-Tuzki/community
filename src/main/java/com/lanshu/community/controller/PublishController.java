package com.lanshu.community.controller;

import com.lanshu.community.mapper.QuestionMapper;
import com.lanshu.community.mapper.UserMapper;
import com.lanshu.community.model.Question;
import com.lanshu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(String title, String description, String tag, HttpServletRequest request, Model model) {

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
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies!=null){
            for (Cookie cookie: cookies) {
                //如果cookie中有key为token的value，取出并去mysql中查找用户信息
                //如果有匹配用户则放入session中，实现持久化登录状态
                if ("token".equals(cookie.getName())) {
                    user = userMapper.findByToken(cookie.getValue());
                    if (user != null){
                        //用户信息放入session
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
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
        //插入问题表
        questionMapper.insert(question);
        //发布成功，返回首页
        return "redirect:/";
    }
}
