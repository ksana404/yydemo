package com.yy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.Order;

/**订单基本表  持久层接口
 * @author Administrator
 *
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order>{

	/**通过 订单id查询该订单所有信息
	 * @param id
	 * @return
	 */
	Order queryOrderByOrderId(@Param("id") String id);
	

}
	