package com.offcn.project.service;

import com.offcn.project.pojo.*;

import java.util.List;

public interface ProjectInfoService {

    /**
     * 获取项目的回报列表
     * @param projectId
     * @return
     */
    List<TReturn> getReturnList(Integer projectId);

    /**
     * 获取系统中的所有项目
     * @return
     */
    List<TProject> findAllProject();

    /**
     * 获取项目图片
     * @param id 项目id
     * @return
     */
    List<TProjectImages> findProjectImage(Integer id);

    /**
     * 获取项目信息
     * @param projectId
     * @return
     */
    TProject findProjectById(Integer projectId);

    /**
     * 获取项目的标签信息
     * @return
     */
    List<TTag> findAllTag();

    /**
     * 获取项目的分类信息
     * @return
     */
    List<TType> findAllType();

    /**
     * 项目的回报详情
     * @param returnId
     * @return
     */
    TReturn findReturnInfo(Integer returnId);

}
