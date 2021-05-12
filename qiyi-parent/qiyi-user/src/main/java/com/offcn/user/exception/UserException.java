package com.offcn.user.exception;

import com.offcn.user.enums.UserExceptionEnum;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 9:56
 * @Description:
 */
public class UserException extends RuntimeException{

    public UserException(UserExceptionEnum userExceptionEnum){
        super(userExceptionEnum.getMsg());
    }
}
