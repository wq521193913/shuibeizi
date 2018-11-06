package com.shuibeizi.common.exception;

/**
 * Created by Administrator on 2016/11/23.
 */
public class CustomException extends RuntimeException {

    private String errorMsg;

    public CustomException(){
        super();
    }

    public CustomException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
