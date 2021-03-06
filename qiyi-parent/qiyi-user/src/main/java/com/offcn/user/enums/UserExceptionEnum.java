package com.offcn.user.enums;

public enum UserExceptionEnum {

    LOGINACCT_EXIST(1,"登录账号已存在"),
    EMAIL_EXIST(2,"邮箱已存在"),
    LOGINACCT_LOCKED(3,"账号已被锁定");

    UserExceptionEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
