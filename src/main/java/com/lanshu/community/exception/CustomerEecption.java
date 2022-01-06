package com.lanshu.community.exception;

public class CustomerEecption extends Exception {

    private Integer code;
    private String message;

    public CustomerEecption(ErrorCodeI errorCodeI){
        code = errorCodeI.getCode();
        message = errorCodeI.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode(){
        return code;
    }
}
