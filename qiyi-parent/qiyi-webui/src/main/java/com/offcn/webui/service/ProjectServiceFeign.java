package com.offcn.webui.service;

import com.offcn.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.ProjectServiceFeignExceptiom;
import com.offcn.webui.vo.ProjectDetailVo;
import com.offcn.webui.vo.ProjectVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "QIYIPROJECT",configuration = FeignConfig.class,fallback = ProjectServiceFeignExceptiom.class)
public interface ProjectServiceFeign {


    @GetMapping("/project/all")
    public AppResponse<List<ProjectVo>> findAllProject();

    @GetMapping("/project/findProjectInfo/{projectId}")
    public AppResponse<ProjectDetailVo> findProjectInfo(@PathVariable("projectId") Integer projectId);

}
