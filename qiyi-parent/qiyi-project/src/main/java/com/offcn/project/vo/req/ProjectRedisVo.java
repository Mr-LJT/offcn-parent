package com.offcn.project.vo.req;

import com.offcn.project.pojo.TReturn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/7 10:07
 * @Description:
 */
@Data
public class ProjectRedisVo implements Serializable {

    @ApiModelProperty("项目的临时token")
    private String projectToken;

    @ApiModelProperty("会员id")
    private String memberid;

    @ApiModelProperty("项目的分类")
    private List<Integer> typeids;

    @ApiModelProperty("项目的标签")
    private List<Integer> tagids;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目的介绍信息")
    private String remark;

    @ApiModelProperty("筹集资金")
    private Integer money;

    @ApiModelProperty("筹集天数")
    private Integer day;

    @ApiModelProperty("项目的大图")
    private String headerImage;

    @ApiModelProperty("项目的详情图片")
    private List<String> detailsImage;

    @ApiModelProperty("项目的回报信息")
    private List<TReturn> projectReturns;

    private static final long serialVersionUID = 1L;
}
