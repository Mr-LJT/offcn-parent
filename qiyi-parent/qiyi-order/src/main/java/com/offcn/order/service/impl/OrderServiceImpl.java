package com.offcn.order.service.impl;

import com.offcn.enums.OrderStatusEnum;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.pojo.TOrder;
import com.offcn.order.pojo.TReturn;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.response.AppResponse;
import com.offcn.utils.AppDateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author LJT
 * @CreateTime: 2021/5/8 21:52
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Override
    public TOrder saveOrder(OrderInfoSubmitVo vo) {
        //1.创建订单对象
        TOrder order = new TOrder();
        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (!StringUtils.isEmpty(memberId)){
            order.setMemberid(Integer.parseInt(memberId));
            //2.复制对象
            BeanUtils.copyProperties(vo,order);
            //订单编号
            String orderNum = UUID.randomUUID().toString().replace("-", "");
            order.setOrdernum(orderNum);
            order.setStatus(OrderStatusEnum.UNPAY.getCode()+"");
            order.setInvoice(vo.getInvoice().toString());
            order.setCreatedate(AppDateUtil.getFormatTime());
            //3.获取项目的回报信息
            AppResponse<List<TReturn>> appResponse = projectServiceFeign.getReturns(vo.getProjectId());
            List<TReturn> returns = appResponse.getData();
            //默认取出第一个回报信息
            TReturn tReturn = returns.get(0);
            Integer money = order.getRtncount() * tReturn.getSupportmoney() + tReturn.getFreight();
            order.setMoney(money);

            //执行保存
            orderMapper.insertSelective(order);
            return order;
        }
        return null;
    }
}
