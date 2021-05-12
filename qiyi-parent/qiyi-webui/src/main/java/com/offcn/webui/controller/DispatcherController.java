package com.offcn.webui.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.service.UserServiceFeign;
import com.offcn.webui.vo.ProjectVo;
import com.offcn.webui.vo.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 19:32
 * @Description:
 */
@Controller
@Slf4j
public class DispatcherController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @RequestMapping("/")
    public String toIndex(Model model){
        //从redis中读取项目集合
        List<ProjectVo> data = (List<ProjectVo>) redisTemplate.opsForValue().get("projectStr");
        if (data == null){
            AppResponse<List<ProjectVo>> allProject = projectServiceFeign.findAllProject();
            data = allProject.getData();
            redisTemplate.opsForValue().set("projectStr",data,1, TimeUnit.HOURS);
        }
        model.addAttribute("projectList",data);
        return "index";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String password, HttpSession session){
        AppResponse<UserRespVo> response = userServiceFeign.login(loginacct, password);
        //获取响应数据
        UserRespVo userRespVo = response.getData();
        log.info("登录账号：{}，密码：{}",loginacct,password);
        log.info("登录的用户数据：{}",userRespVo);
        if (null == userRespVo){
            //账号不存在，跳转登录界面
            return "redirect:/login.html";
        }
        //用户存在，执行登录，把用户信息保存到session中
        session.setAttribute("sessionMember",userRespVo);
        //从session中获取前缀
        String preUrl = (String) session.getAttribute("preUrl");
        //如果获取的前缀不存在
        if (StringUtils.isEmpty(preUrl)){
            return "redirect:/";
        }
        return "redirect:/" + preUrl;
    }

}
