package com.lanshu.community.exception;

public class QuestionException extends Exception{

    public QuestionException(){
        super();
    }

    public QuestionException(String message){
        /*调用父类Exception的有参构造，再调用父类Throwable的有参构造
        * message赋值给detailMessage，调用QuestionException.getMessage会返回detailMessage*/
        super(message);
    }
}
