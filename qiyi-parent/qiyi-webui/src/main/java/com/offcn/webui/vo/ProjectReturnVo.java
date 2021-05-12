package com.offcn.webui.vo;

import com.offcn.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LJT
 * @CreateTime: 2021/5/7 15:03
 * @Description:
 */
@Data
@ApiModel
public class ProjectReturnVo extends BaseVo implements Serializable {

    @ApiModelProperty(value = "项目的临时token",required = true)
    private String projectToken;

    @ApiModelProperty(value = "回报类型 0：实物回报  1：虚拟物品回报",required = true)
    private String type;

    @ApiModelProperty(value = "支持金额",required = true)
    private Integer supportmoney;

    @ApiModelProperty(value = "回报内容",required = true)
    private String content;

    @ApiModelProperty(value = "单笔限购",required = true,example = "0")
    private Integer signalpurchase;

    @ApiModelProperty(value = "限购数量",required = true,example = "0")
    private Integer purchase;

    @ApiModelProperty(value = "运费",required = true,example = "0")
    private Integer freight;

    @ApiModelProperty(value = "是否开发票 0：不开发票  1：开发票",required = true)
    private String invoice;

    @ApiModelProperty(value = "回报时间(天)",required = true,example = "0")
    private Integer rtndate;

    private static final long serialVersionUID = 1L;
}
