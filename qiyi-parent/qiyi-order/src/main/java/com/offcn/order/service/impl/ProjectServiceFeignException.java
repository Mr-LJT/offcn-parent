package com.offcn.order.service.impl;

import com.offcn.order.pojo.TReturn;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.response.AppResponse;

import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/8 21:48
 * @Description:
 */
public class ProjectServiceFeignException implements ProjectServiceFeign {
    @Override
    public AppResponse<List<TReturn>> getReturns(Integer projectId) {
        AppResponse<List<TReturn>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务失败【查询项目回报列表】");
        return null;
    }
}
