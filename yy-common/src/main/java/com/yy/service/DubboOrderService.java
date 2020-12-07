package com.yy.service;

import com.yy.pojo.Order;

public interface DubboOrderService {

	

	/**通过 用户提交的订单信息  新增订单.(购物车信息,和 用户填写的物流信息)
	 * 返回订单ID
	 * @param order
	 * @return
	 */
	String addOrder(Order order);

	/**根据订单ID查询 orderId 订单所有 信息(Mybatis-Plus版本)
	 * @param orderId
	 * @return
	 */
	Order queryOrderByOrderId(String orderId);

	/**根据订单ID查询 orderId 订单所有 信息(MySQL版本)
	 * @param id
	 * @return
	 */
	Order queryOrderByOrderIdMysql(String id);

}
