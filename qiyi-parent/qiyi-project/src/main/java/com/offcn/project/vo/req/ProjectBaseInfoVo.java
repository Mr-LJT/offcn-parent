package com.offcn.project.vo.req;

import com.offcn.project.pojo.TReturn;
import com.offcn.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/7 10:47
 * @Description:
 */
@ApiModel
@Data
public class ProjectBaseInfoVo extends BaseVo {

    @ApiModelProperty("项目的临时token")
    private String projectToken;

    @ApiModelProperty("项目的分类")
    private List<Integer> typeids;

    @ApiModelProperty("项目的标签")
    private List<Integer> tagids;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目的介绍信息")
    private String remark;

    @ApiModelProperty(value = "筹集资金",example = "0")
    private Integer money;

    @ApiModelProperty(value = "筹集天数",example = "0")
    private Integer day;

    @ApiModelProperty("项目的大图")
    private String headerImage;

    @ApiModelProperty("项目的详情图片")
    private List<String> detailsImage;

    private static final long serialVersionUID = 1L;

}
