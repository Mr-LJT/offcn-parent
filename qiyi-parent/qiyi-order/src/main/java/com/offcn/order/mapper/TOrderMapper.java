package com.offcn.order.mapper;

import com.offcn.order.pojo.TOrder;
import com.offcn.order.pojo.TOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOrderMapper {
    long countByExample(TOrderExample example);

    int deleteByExample(TOrderExample example);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    List<TOrder> selectByExample(TOrderExample example);

    int updateByExampleSelective(@Param("record") TOrder record, @Param("example") TOrderExample example);

    int updateByExample(@Param("record") TOrder record, @Param("example") TOrderExample example);
}