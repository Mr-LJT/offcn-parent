package com.offcn.order.controller;

import com.offcn.order.pojo.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.response.AppResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LJT
 * @CreateTime: 2021/5/8 22:05
 * @Description:
 */
@Api(tags = "保存订单")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("保存订单")
    @PostMapping("/createOrder")
    public AppResponse<TOrder> createOrder(@RequestBody OrderInfoSubmitVo vo){
        String memberId = redisTemplate.opsForValue().get(vo.getAccessToken());
        if (StringUtils.isEmpty(memberId)){
            AppResponse<TOrder> fail = AppResponse.fail(null);
            fail.setMsg("无此权限，请先登录");
            return fail;
        }
        try {
            TOrder tOrder = orderService.saveOrder(vo);
            AppResponse<TOrder> response = AppResponse.ok(tOrder);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

}
