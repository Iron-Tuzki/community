package com.lanshu.community.mapper;

import com.lanshu.community.model.Question;

import java.util.List;

public interface QuestionMapper {
    void insert(Question question);

    List<Question> select(Integer offset, Integer size);

    Integer allCount();
}
