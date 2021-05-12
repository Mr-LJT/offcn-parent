package com.offcn.webui.service.impl;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.ProjectDetailVo;
import com.offcn.webui.vo.ProjectVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 20:43
 * @Description:
 */
@Slf4j
@Component
public class ProjectServiceFeignExceptiom implements ProjectServiceFeign {


    @Override
    public AppResponse<List<ProjectVo>> findAllProject() {
        AppResponse<List<ProjectVo>> fail = AppResponse.fail(null);
        fail.setMsg("远程调用失败【查询所有项目】");
        return fail;
    }

    @Override
    public AppResponse<ProjectDetailVo> findProjectInfo(Integer projectId) {
        AppResponse<ProjectDetailVo> fail = AppResponse.fail(null);
        fail.setMsg("远程调用失败【获取项目详情】");
        return fail;
    }
}
