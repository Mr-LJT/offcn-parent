package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.pojo.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.sms.SmsTemplate;
import com.offcn.user.vo.req.UserRegisterVo;
import com.offcn.user.vo.resp.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author LJT
 * @CreateTime: 2021/5/1 13:57
 * @Description:
 */
@RestController
@Api(tags = "用户登录、用户注册(包括忘记密码)")
@Slf4j
public class UserLoginController {

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phoneNo",value = "手机号",required = true)
    })
    @PostMapping("/sendCode")
    public AppResponse<Object> sendCoe(String phoneNo){

        //1.生成验证码保存到服务器，准备与用户提交上来的验证码比对
        String code = UUID.randomUUID().toString().substring(0, 4);
        //2.保存验证码和手机号的对应关系,设置验证码的过期时间
        redisTemplate.opsForValue().set(phoneNo,code,60*5, TimeUnit.MINUTES);
        //3.短信发送 构造参数
        Map<String,String> querys = new HashMap<>();
        querys.put("mobile",phoneNo);
        querys.put("param","code:"+code);
        querys.put("tpl_id","TP1711063");
        //4.发送短信
        String sendCode = smsTemplate.sendCode(querys);
        if (sendCode.equals("") || sendCode.equals("fail")){
            //短信发送失败
            return AppResponse.fail("短信发送失败");
        }
        return AppResponse.ok(sendCode);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public AppResponse<Object> register(UserRegisterVo registerVo){
        //校验验证码
        String code = redisTemplate.opsForValue().get(registerVo.getLoginacct());
        if (!StringUtils.isEmpty(code)){
            boolean b = code.equalsIgnoreCase(registerVo.getCode());
            if (b){
                //将vo转换为当前业务能用的数据对象
                TMember member = new TMember();
                BeanUtils.copyProperties(registerVo,member);
                //将用户信息注册到数据库
                try {
                    userService.registerUser(member);
                    log.debug("用户注册成功，信息为：{}",member.getLoginacct());
                    //注册成功后,删除验证码
                    redisTemplate.delete(registerVo.getLoginacct());
                    return AppResponse.ok("注册成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    return AppResponse.fail("注册失败");
                }
            }else{
                return AppResponse.fail("验证码错误");
            }
        }else{
            return AppResponse.fail("验证码过期，请重新获取");
        }
    }

    @ApiOperation("用户登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @PostMapping("/login")
    public AppResponse<UserRespVo> login(String username,String password){
        //1.尝试登录
        TMember member = userService.login(username, password);
        if (null == member){
            //登录失败
            AppResponse<UserRespVo> fail = AppResponse.fail(null);
            fail.setMsg("用户名或密码错误");
            return fail;
        }
        //2.登录成功
        String token = UUID.randomUUID().toString().replace("-", "");
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(member,userRespVo);
        userRespVo.setAccessToken(token);
        //3.根据令牌查询用户的id信息
        redisTemplate.opsForValue().set(token,member.getId()+"",2,TimeUnit.HOURS);
        return AppResponse.ok(userRespVo);
    }

    @ApiOperation("获取用户基本信息")
    @GetMapping("/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id){
        TMember member = userService.findMemberById(id);
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(member,userRespVo);
        return AppResponse.ok(userRespVo);
    }

}
