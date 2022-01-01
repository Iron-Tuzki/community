package com.lanshu.community.service;

import com.lanshu.community.dto.PaginationDto;
import com.lanshu.community.dto.QuestionDto;
import com.lanshu.community.mapper.QuestionMapper;
import com.lanshu.community.mapper.UserMapper;
import com.lanshu.community.model.Question;
import com.lanshu.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDto list(Integer page, Integer size){

        PaginationDto paginationDto = new PaginationDto();
        //查找问题总数
        Integer totalQuestions = questionMapper.allCount();
        //总页数
        Integer totalPages = paginationDto.getTotalPages(totalQuestions, size);
        //优化，要在查询数据库前优化page，否则无数据
        if (page < 1) page = 1;
        if (page > totalPages) {
            page = totalPages;
        }
        //根据当前页码设置分页各个模块的显示状态
        paginationDto.setPageConfiguration(page);

        //mysql 分页语法 limit startIndex, length
        //根据page 和 size 查找文章
        Integer offset = (page-1)*size;
        List<Question> questionList = questionMapper.select(offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        //遍历每个问题，把问题和问题创建者一起放入questionDto
        for (Question question:questionList) {
            QuestionDto questionDto = new QuestionDto();
            User user = userMapper.findById(question.getCreator());
            //相同属性复制
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        //问题列表封装到 paginationDto
        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }
}
