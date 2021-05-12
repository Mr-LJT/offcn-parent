package com.offcn.project.controller;

import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.util.OssTemplate;
import com.offcn.project.vo.resp.ProjectDetailVo;
import com.offcn.project.vo.resp.ProjectVo;
import com.offcn.response.AppResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 15:53
 * @Description:
 */
@Api(tags = "项目的基本功能（文件上传，项目信息获取）")
@Slf4j
@RequestMapping("/project")
@RestController
public class ProjectInfoController {

    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public AppResponse<Map<String,Object>> upload(@RequestParam("file")MultipartFile[] files) throws IOException {
        Map<String,Object> map = new HashMap<>();
        List list = new ArrayList();

        if (files != null && files.length > 0){
            for (MultipartFile file : files) {
                if (!file.isEmpty()){
                    String upload = ossTemplate.upload(file.getInputStream(), file.getOriginalFilename());
                    list.add(upload);
                }
            }
        }
        map.put("urls",list);
        log.debug("OssTemplate信息：{}，文件上传成功访问路径：{}",ossTemplate,list);
        return AppResponse.ok(map);
    }

    @ApiOperation("获取项目的回报列表")
    @GetMapping("/details/returns/{projectId}")
    public AppResponse<List<TReturn>> getReturns(@PathVariable("projectId") Integer projectId){
        List<TReturn> returnList = projectInfoService.getReturnList(projectId);
        return AppResponse.ok(returnList);
    }

    @ApiOperation("获取系统中的所有项目")
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> findAllProject(){
        //1.创建集合，存储全部的项目vo
        List<ProjectVo> projectVos = new ArrayList<>();
        //2.查询所有项目
        List<TProject> allProject = projectInfoService.findAllProject();
        for (TProject project : allProject) {
            Integer projectId = project.getId();
            List<TProjectImages> projectImage = projectInfoService.findProjectImage(projectId);
            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties(project,projectVo);
            for (TProjectImages images : projectImage) {
                if (images.getImgtype() == 0){
                    projectVo.setHeaderImage(images.getImgurl());
                }
            }
            projectVos.add(projectVo);
        }
        return AppResponse.ok(projectVos);
    }

    @ApiOperation("获取项目详情")
    @GetMapping("/findProjectInfo/{projectId}")
    public AppResponse<ProjectDetailVo> findProjectInfo(@PathVariable("projectId") Integer projectId){
        TProject project = projectInfoService.findProjectById(projectId);
        ProjectDetailVo projectDetailVo = new ProjectDetailVo();
        //1.查询当前这个项目的所有图片
        List<TProjectImages> projectImage = projectInfoService.findProjectImage(project.getId());
        List<String> detailsImage = projectDetailVo.getDetailsImage();
        if (detailsImage == null){
            detailsImage = new ArrayList<>();
        }
        for (TProjectImages images : projectImage) {
            if (images.getImgtype().equals("0")){
                projectDetailVo.setHeaderImage(images.getImgurl());
            }else{
                detailsImage.add(images.getImgurl());
            }
        }
        projectDetailVo.setDetailsImage(detailsImage);
        //项目的回报信息
        List<TReturn> returnList = projectInfoService.getReturnList(projectId);
        projectDetailVo.setProjectReturns(returnList);
        BeanUtils.copyProperties(project,projectDetailVo);
        return AppResponse.ok(projectDetailVo);
    }

    @ApiOperation("获取项目的所有标签")
    @GetMapping("/findALlTag")
    public AppResponse<List<TTag>> findAllTag(){
        return AppResponse.ok(projectInfoService.findAllTag());
    }

    @ApiOperation("获取项目的所有分类")
    @GetMapping("/findALlType")
    public AppResponse<List<TType>> findAllType(){
        return AppResponse.ok(projectInfoService.findAllType());
    }

    @ApiOperation("获取项目的回报详情")
    @GetMapping("/returns/info/{returnId}")
    public AppResponse<TReturn> findReturnInfo(@PathVariable("returnId") Integer returnId){
        return AppResponse.ok(projectInfoService.findReturnInfo(returnId));
    }



}
