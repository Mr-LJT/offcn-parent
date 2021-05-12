package com.offcn.webui.controller;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.ProjectDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 21:17
 * @Description:
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @RequestMapping("/projectInfo")
    public String index(Integer id, Model model, HttpSession session){
        AppResponse<ProjectDetailVo> projectInfo = projectServiceFeign.findProjectInfo(id);
        ProjectDetailVo data = projectInfo.getData();
        model.addAttribute("DetailVo",data);
        session.setAttribute("DetailVo",data);
        return "project/project";
    }
}
