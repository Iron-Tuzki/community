package com.lanshu.community.service;

import com.lanshu.community.enums.CommentType;
import com.lanshu.community.exception.CustomerEecption;
import com.lanshu.community.exception.ErrorCode;
import com.lanshu.community.mapper.CommentMapper;
import com.lanshu.community.mapper.QuestionMapper;
import com.lanshu.community.model.Comment;
import com.lanshu.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment) throws CustomerEecption {
        //评论前先检查问题是否存在
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomerEecption(ErrorCode.TARGET_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentType.contain(comment.getType())){
            throw new CustomerEecption(ErrorCode.TYPE_ERROR);
        }
        if (comment.getType() == CommentType.COMMENT.getType()){
            //回复的是评论，通过parentId去数据库中查找该父类评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null) {
                throw new CustomerEecption(ErrorCode.COMMENT_NOT_FOUND);
            }
            //存在父评论，则向数据库插入子评论
            commentMapper.insert(comment);
        } else {
            //回复的是问题，查找父问题
            Question parentQuestion = questionMapper.findById(comment.getParentId());
            if (parentQuestion == null){
                throw new CustomerEecption(ErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //评论数加一
            Question question = new Question();
            question.setId(parentQuestion.getId());
            question.setCommentCount(parentQuestion.getCommentCount());
            questionMapper.updateByIdSelective(question);
        }
    }
}
