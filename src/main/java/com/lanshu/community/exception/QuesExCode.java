package com.lanshu.community.exception;

public enum  QuesExCode implements QuesExCodeI {

    QUESTION_NOT_FOUND("问题不存在");
    private String message;
    QuesExCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
