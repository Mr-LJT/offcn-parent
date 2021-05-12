package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/9 15:08
 * @Description:
 */
@Api(tags = "获取用户信息、获取用户地址")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("/获取用户地址")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "accessToken",value = "用户登录的令牌",required = true)
    })
    @GetMapping("/findAddressList")
    public AppResponse<List<TMemberAddress>> findAddressList(String accessToken){
        //获取用户id
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)){
            return AppResponse.fail(null);
        }
        List<TMemberAddress> addressList = userService.findAddressList(Integer.parseInt(memberId));
        return AppResponse.ok(addressList);
    }


}
