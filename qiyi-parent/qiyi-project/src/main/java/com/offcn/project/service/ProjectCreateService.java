package com.offcn.project.service;

import com.offcn.enums.ProjectStatusEnum;
import com.offcn.project.vo.req.ProjectRedisVo;

public interface ProjectCreateService {

    /**
     * 初始化项目
     * @param memberId
     * @return
     */
    public String initCreateProject(Integer memberId);

    /**
     * 保存项目信息
     * @param statusEnum
     * @param redisVo
     */
    public void saveProjectInfo(ProjectStatusEnum statusEnum, ProjectRedisVo redisVo);

}
