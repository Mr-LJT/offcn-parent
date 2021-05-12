package com.offcn.webui.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LJT
 * @CreateTime: 2021/5/9 15:24
 * @Description:
 */
@Data
public class ProjectVo implements Serializable {

    private Integer memberid;

    private Integer id;

    private String name;

    private String remark;

    private String headerImage;

    private static final long serialVersionUID = 1L;

}
