package com.offcn.webui.service.impl;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.UserServiceFeign;
import com.offcn.webui.vo.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 20:13
 * @Description:
 */
@Slf4j
@Component
public class UserServiceFeignException implements UserServiceFeign {

    @Override
    public AppResponse<UserRespVo> login(String username, String password){
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务失败【登录】");
        return fail;
    }
}
