package com.offcn.webui.service;

import com.offcn.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.UserServiceFeignException;
import com.offcn.webui.vo.UserRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 20:11
 * @Description:
 */
@FeignClient(value = "QIYIUSER",configuration = FeignConfig.class,fallback = UserServiceFeignException.class)
public interface UserServiceFeign {

    @PostMapping("/login")
    AppResponse<UserRespVo> login(@RequestParam("username") String username,@RequestParam("password") String password);
}
