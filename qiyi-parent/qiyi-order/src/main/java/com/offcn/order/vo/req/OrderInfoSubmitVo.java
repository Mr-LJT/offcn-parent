package com.offcn.order.vo.req;

import lombok.Data;

/**
 * @author LJT
 * @CreateTime: 2021/5/8 20:49
 * @Description:
 */
@Data
public class OrderInfoSubmitVo {

    private String accessToken;

    private Integer projectId;

    private Integer returnid;

    private Integer rtncount;

    private String address;

    private Byte invoice;

    private String invoicetitle;

    private String remark;


}
