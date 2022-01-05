package com.lanshu.community.mapper;

import com.lanshu.community.model.Question;

import java.util.List;

public interface QuestionMapper {
    
    void insert(Question question);

    Integer allCount();

    List<Question> select(Integer offset, Integer size);

    Integer allCountByCreator(Integer id);

    List<Question> selectByCreator(Integer id, Integer offset, Integer size);

    Question findById(Integer id);

    void update(Question question);

    Integer findId(Question question);

    void deleteById(Integer id);
}
