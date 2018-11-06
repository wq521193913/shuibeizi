package com.shuibeizi.common.enumerate;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/25 0025 11:25
 * @modified:
 */
public enum ResultCode {

    SUCCESS(1000,"操作成功"),
    FAIL(1001,"操作失败"),
    SYS_ERROR(1002,"系统繁忙, 请稍后重试"),
    LOGIN_EXPIRE(1003,"登录失效"),

    ;

    private int code;

    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public ResultCode setCode(int code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ResultCode setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
