package com.lanshu.community.exception;

public class QuesEx extends Exception {

    private String message;

    public QuesEx(QuesExCodeI quesExCodeI){
        message = quesExCodeI.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
