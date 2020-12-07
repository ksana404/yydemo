package com.yy.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.OrderItem;

/**
 * 订单商品表  持久层接口
 * @author Administrator
 *
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}
