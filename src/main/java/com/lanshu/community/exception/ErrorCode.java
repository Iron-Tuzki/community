package com.lanshu.community.exception;

public enum ErrorCode implements ErrorCodeI{

    TARGET_NOT_FOUND(2001, "帖子已删除"),
    QUESTION_NOT_FOUND(2002,"问题不存在"),
    COMMENT_NOT_FOUND(2003,"回复已删除"),
    TYPE_ERROR(2004, "该回复类型不存在"),
    NOT_LOGIN(2010,"用户未登录");

    private Integer code;
    private String message;

    //枚举构造方法
    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
