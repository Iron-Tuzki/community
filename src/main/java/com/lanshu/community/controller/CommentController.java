package com.lanshu.community.controller;


import com.lanshu.community.dto.CommentDto;
import com.lanshu.community.dto.ResultDto;
import com.lanshu.community.exception.CustomerEecption;
import com.lanshu.community.exception.ErrorCode;
import com.lanshu.community.model.Comment;
import com.lanshu.community.model.User;
import com.lanshu.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /*@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
        写入到response对象的body区，通常用来返回JSON数据或者是XML数据，需要注意的呢，在使用此注解之后不会再走试图处理器
        而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据。*/
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request) throws CustomerEecption {
        /*RequestBody 页面发送一个JSON格式的对象，服务端反序列化为CommentDto*/
        User user = (User)request.getSession().getAttribute("user");
        if (user==null)
            //抛出该异常，并携带枚举信息
            throw new CustomerEecption(ErrorCode.NOT_LOGIN);
            //return ResultDto.errorOf(ErrorCode.NOT_LOGIN);
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentatorId(user.getId());
        comment.setLikeCount(1);
        commentService.insert(comment);

        //返回一个ResultDto对象到页面，包含了code message
        return ResultDto.okOf();
    }

}
