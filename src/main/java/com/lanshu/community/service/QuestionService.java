package com.lanshu.community.service;

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

    public List<QuestionDto> list(){

        List<Question> questionList = questionMapper.select();
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question:questionList) {
            QuestionDto questionDto = new QuestionDto();
            User user = userMapper.findById(question.getCreator());
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }

}
