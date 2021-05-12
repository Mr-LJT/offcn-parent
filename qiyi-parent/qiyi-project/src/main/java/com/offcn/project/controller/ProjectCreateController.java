package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.enums.ProjectStatusEnum;
import com.offcn.project.contants.ProjectContants;
import com.offcn.project.pojo.TReturn;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import com.offcn.response.AppResponse;
import com.offcn.vo.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/7 10:24
 * @Description:
 */
@Api(tags = "项目的基本功能(创建项目，保存项目，项目信息查看)")
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectCreateController {

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("/项目发起的第一步，阅读并同意协议")
    @PostMapping("/init")
    public AppResponse<String> init(BaseVo vo){
        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get(accessToken);
        //通过登录的令牌获取项目id
        if (StringUtils.isEmpty(memberId)){
            return AppResponse.fail("无此权限请先登录");
        }
        int id = Integer.parseInt(memberId);
        String projectToken = projectCreateService.initCreateProject(id);
        return AppResponse.ok(projectToken);
    }

    @ApiOperation("项目发起的第二步，保存项目的基本信息")
    @PostMapping("/savebaseinfo")
    public AppResponse<String> saveBaseInfo(ProjectBaseInfoVo vo){
        //取出redis中存储的redis信息
        String projectInfo = redisTemplate.opsForValue().get(ProjectContants.TEMP_PROJECT_PREFIX + vo.getProjectToken());
        ProjectRedisVo projectRedisVo = JSON.parseObject(projectInfo, ProjectRedisVo.class);

        //将页面收集来的数据，复制到和redis映射的vo上
        BeanUtils.copyProperties(vo,projectRedisVo);
        //把复制好的vo再次转换为jsonstring，保存到redis
        String jsonString = JSON.toJSONString(projectRedisVo);
        redisTemplate.opsForValue().set(ProjectContants.TEMP_PROJECT_PREFIX+vo.getProjectToken(),jsonString);
        return AppResponse.ok("OK!");
    }

    @ApiOperation("项目发起的第三步，保存项目的回报信息")
    @PostMapping("/savereturn")
    public AppResponse<Object> saveReturn(@RequestBody List<ProjectReturnVo> vo){
        ProjectReturnVo returnVo = vo.get(0);
        String token = returnVo.getProjectToken();
        //1.取出redis中存储的项目信息
        String jsonString = redisTemplate.opsForValue().get(ProjectContants.TEMP_PROJECT_PREFIX + token);
        //2.转换为自定义vo
        ProjectRedisVo projectRedisVo = JSON.parseObject(jsonString, ProjectRedisVo.class);
        //3.把页面收集到的信息封装到projectRedisVo 重新存入redis
        List<TReturn> returns = new ArrayList<>();
        for (ProjectReturnVo projectReturnVo : vo) {
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(projectReturnVo,tReturn);
            returns.add(tReturn);
        }
        projectRedisVo.setProjectReturns(returns);
        String string = JSON.toJSONString(projectRedisVo);
        redisTemplate.opsForValue().set(ProjectContants.TEMP_PROJECT_PREFIX+token,string);
        return AppResponse.ok("OK!");
    }

    @ApiOperation("项目发起的第四步，保存项目信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "accessToken",value = "用户登录的令牌",required = true),
            @ApiImplicitParam(name = "projectToken",value = "项目的临时token",required = true),
            @ApiImplicitParam(name = "ops",value = "用户操作类型 0：保存草稿  1：提交审核",required = true)
    })
    @PostMapping("/submit")
    public AppResponse<Object> submit(String accessToken ,String projectToken,String ops){
        //前置校验
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)){
            return AppResponse.fail("无权限，请先登录!");
        }
        String projectJSONString = redisTemplate.opsForValue().get(ProjectContants.TEMP_PROJECT_PREFIX + projectToken);
        ProjectRedisVo projectRedisVo = JSON.parseObject(projectJSONString, ProjectRedisVo.class);
        //判断用户操作类型 不能为空
        if (!StringUtils.isEmpty(ops)){
            //判断操作类型
            if (ops.equals("1")){
                //获取项目状态的枚举
                ProjectStatusEnum submitAuth = ProjectStatusEnum.SUBMIT_AUTH;
                projectCreateService.saveProjectInfo(submitAuth,projectRedisVo);
                return AppResponse.ok(null);
            }else if (ops.equals("0")){
                ProjectStatusEnum draft = ProjectStatusEnum.DRAFT;
                projectCreateService.saveProjectInfo(draft,projectRedisVo);
                return AppResponse.ok(null);
            }else{
                AppResponse<Object> fail = AppResponse.fail(null);
                fail.setMsg("不支持此类型的操作");
                return fail;
            }
        }
        return AppResponse.fail(null);
    }
}
