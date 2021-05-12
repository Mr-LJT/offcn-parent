package com.offcn.user.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 10:12
 * @Description:
 */
@ApiModel
@Data
public class UserRegisterVo implements Serializable {

    @ApiModelProperty("手机号")
    private String loginacct;

    @ApiModelProperty("密码")
    private String userpswd;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("验证码")
    private String code;

    private static final long serialVersionUID = 1L;

}
