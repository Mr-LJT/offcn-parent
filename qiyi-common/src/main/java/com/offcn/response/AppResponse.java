package com.offcn.response;

import com.offcn.enums.ResponseCodeEnum;

/**
 * @author LJT
 * @CreateTime: 2021/5/1 13:37
 * @Description: 应用的统一返回结果
 */
public class AppResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public static void fail() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 响应成功
     */
    public static<T> AppResponse<T> ok(T data){
        AppResponse<T> appResponse = new AppResponse<T>();
        appResponse.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        appResponse.setCode(ResponseCodeEnum.SUCCESS.getCode());

         appResponse.setData(data);
        return appResponse;
    }

    /**
     * 响应失败
     */
    public static<T> AppResponse<T> fail(T data){
        AppResponse<T> appResponse = new AppResponse<T>();
        appResponse.setMsg(ResponseCodeEnum.FAIL.getMsg());
        appResponse.setCode(ResponseCodeEnum.FAIL.getCode());
        appResponse.setData(data);
        return appResponse;
    }

}
