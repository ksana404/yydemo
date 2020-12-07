package com.yy.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.OrderShipping;

/**订单物流表  持久层接口
 * @author Administrator
 *
 */
@Mapper
public interface OrderShippingMapper extends BaseMapper<OrderShipping> {

}
