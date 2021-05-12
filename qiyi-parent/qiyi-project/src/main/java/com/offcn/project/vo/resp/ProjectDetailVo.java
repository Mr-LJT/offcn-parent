package com.offcn.project.vo.resp;

import com.offcn.project.pojo.TReturn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/10 19:38
 * @Description:
 */
@Data
@ApiModel("项目详情映射实体类")
public class ProjectDetailVo implements Serializable {

    @ApiModelProperty("项目id")
    private Integer id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("项目介绍")
    private String remark;

    @ApiModelProperty("筹集资金")
    private Long money;

    @ApiModelProperty("项目状态 0：即将开始 1：众筹中 2：众筹成功 3：众筹失败")
    private String status;

    @ApiModelProperty("发布日期")
    private String deploydate;

    @ApiModelProperty("支持金额")
    private Long supportmoney;

    @ApiModelProperty("支持者数量")
    private Integer supporter;

    @ApiModelProperty("筹集天数")
    private Integer day;

    @ApiModelProperty("完成度")
    private Integer completion;

    @ApiModelProperty("关注者数量")
    private Integer follower;

    @ApiModelProperty("项目大图")
    private String headerImage;

    @ApiModelProperty("项目详情")
    private List<String> detailsImage;

    @ApiModelProperty("项目的回报信息")
    private List<TReturn> projectReturns;

    private static final long serialVersionUID = 1L;
}
