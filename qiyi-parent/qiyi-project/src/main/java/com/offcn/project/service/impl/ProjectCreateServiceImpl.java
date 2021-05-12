package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.enums.ProjectStatusEnum;
import com.offcn.project.contants.ProjectContants;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author LJT
 * @CreateTime: 2021/5/7 10:19
 * @Description:
 */
@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TProjectMapper projectMapper;

    @Autowired
    private TProjectTagMapper projectTagMapper;

    @Autowired
    private TProjectImagesMapper projectImagesMapper;

    @Autowired
    private TProjectTypeMapper projectTypeMapper;

    @Autowired
    private TReturnMapper returnMapper;

    @Override
    public String initCreateProject(Integer memberId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        //项目的临时对象
        ProjectRedisVo projectRedisVo = new ProjectRedisVo();
        projectRedisVo.setMemberid(memberId+"");
        String jsonString = JSON.toJSONString(projectRedisVo);
        redisTemplate.opsForValue().set(ProjectContants.TEMP_PROJECT_PREFIX+token,jsonString);
        return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnum statusEnum, ProjectRedisVo redisVo) {
        TProject tProject = new TProject();
        BeanUtils.copyProperties(redisVo,tProject);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tProject.setCreatedate(sdf.format(new Date()));
        tProject.setStatus(statusEnum.getCode()+"");
        //保存项目基本信息
        projectMapper.insertSelective(tProject);
        Integer projectId = tProject.getId();

        //保存项目大图
        String headerImage = redisVo.getHeaderImage();
        TProjectImages images = new TProjectImages(null, projectId, headerImage, ProjectImageTypeEnume.HEADER.getCode());
        projectImagesMapper.insertSelective(images);

        //保存项目的详情图片
        List<String> detailsImage = redisVo.getDetailsImage();
        for (String string : detailsImage) {
            TProjectImages projectImages = new TProjectImages(null, projectId, string, ProjectImageTypeEnume.DETAILS.getCode());
            projectImagesMapper.insertSelective(projectImages);
        }
        //保存项目的标签信息
        List<Integer> tagids = redisVo.getTagids();
        for (Integer tagid : tagids) {
            TProjectTag projectTag = new TProjectTag(null, projectId, tagid);
            projectTagMapper.insertSelective(projectTag);
        }
        //保存项目的分类信息
        List<Integer> typeids = redisVo.getTypeids();
        for (Integer typeid : typeids) {
            TProjectType projectType = new TProjectType(null, projectId, typeid);
            projectTypeMapper.insertSelective(projectType);
        }
        //保存项目的回报信息
        List<TReturn> returns = redisVo.getProjectReturns();
        for (TReturn tReturn : returns) {
            tReturn.setProjectid(projectId);
            returnMapper.insertSelective(tReturn);
        }
        //删除redis中的临时数据
        redisTemplate.delete(ProjectContants.TEMP_PROJECT_PREFIX+redisVo.getProjectToken());
    }
}
