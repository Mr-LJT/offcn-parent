package com.offcn.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 10:45
 * @Description: 用户登录成功以后返回的信息
 */
@ApiModel
@Data
public class UserRespVo implements Serializable {

    @ApiModelProperty("用户权限令牌，以后每次访问都需要携带")
    private String accessToken;

    private String loginacct;

    private String username;

    private String email;

    private String authstatus;

    private String realname;

    private String cardnum;

    private String accttype;

    private static final long serialVersionUID = 1L;
}
