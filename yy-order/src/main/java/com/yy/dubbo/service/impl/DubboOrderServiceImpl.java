package com.yy.dubbo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.mapper.OrderItemMapper;
import com.yy.mapper.OrderMapper;
import com.yy.mapper.OrderShippingMapper;
import com.yy.pojo.Order;
import com.yy.pojo.OrderItem;
import com.yy.pojo.OrderShipping;
import com.yy.service.DubboOrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DubboOrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper; //订单基本表  持久层接口
	
	@Autowired
	private OrderItemMapper orderItemMapper; //订单商品表  持久层接口
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;  //订单物流表  持久层接口

	@Transactional
	@Override
	public String addOrder(Order order) {
		//0.OrderId准备
		String orderId = getOrderIdByUserId(order.getUserId());
		
		//1.Order 订单基本表  数据插入
		// 订单ID 订单状态  创建-修改时间
		//private Integer status;	//状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭
		order.setOrderId(orderId).setStatus(1).setCreated(new Date()).setUpdated(order.getCreated());
		//order.setOrderId(orderId).setCreated(new Date()).setUpdated(order.getCreated());
		int orderInsert = orderMapper.insert(order);
		//输入插入失败 返回null,还是爆出运行异常???
		if(orderInsert!=1) {
			return null;
		}
		
		//2.OrderItem 订单商品表 数据插入
		// 数据从 Order中获取    
		//订单ID  创建-修改时间
		List<OrderItem> orderItems = order.getOrderItems();
		int orderItemInsert=0;
		for(OrderItem orderItem:orderItems) {
			orderItem.setOrderId(orderId).setCreated(new Date()).setUpdated(order.getCreated());
			 orderItemInsert = orderItemMapper.insert(orderItem);
			if(orderItemInsert!=1) {
				return null;
				//在循环中的 return会break循环吗?
				//
			}
			 
		}
		
		
		//3.OrderShipping 订单物流表 数据插入
		// 数据从 Order中获取    
		//订单ID  创建-修改时间
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId).setCreated(new Date()).setUpdated(order.getCreated());
		int orderShippingInsert = orderShippingMapper.insert(orderShipping);
		if(orderShippingInsert!=1) {
			return null;
		}
		
		return orderId;
	}

   /**根据用户获取OrderId的方法
    *-----------------
    *详情  截取当前时间毫秒数后四位,拼接到用户Id后面,形成订单ID.
 * @param userId
 * @return
 */
public String getOrderIdByUserId(Long userId){
	   //获取当前毫秒数  实例:1607093008573
	   long currentTimeMillis = System.currentTimeMillis();
	 //转为String类型
		String numTimeStr = currentTimeMillis+"";
		//截取后四位
		String substring = numTimeStr.substring(numTimeStr.length()-4);
		//拼接到用户ID后面
		String orderId =userId + substring;
	return orderId;
		
	}

@Override
public Order queryOrderByOrderId(String orderId) {
	//和插入数据正好相反
	//1 获取 orderShipping  订单物流信息
	OrderShipping orderShipping= new OrderShipping();
	orderShipping.setOrderId(orderId);
	QueryWrapper<OrderShipping> queryWrapperShippingMapper =new QueryWrapper<>(orderShipping);
	//queryWrapperShippingMapper.eq("order_id", orderId);
	orderShipping = orderShippingMapper.selectOne(queryWrapperShippingMapper);
	
	
	//2 获取 OrderItem 订单商品表信息 OrderItemMapper
	OrderItem orderItem =new OrderItem();
	orderItem.setOrderId(orderId);
	QueryWrapper<OrderItem> queryWrapperOrderItem =new QueryWrapper<>(orderItem);
	//查询所有的orderI符合的商品信息
	List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapperOrderItem);
	
	//3 获取 Order 订单基本信息 Order,把订单物流信息 和订单商品信息 添加到订单信息当中.
	// ???因为成功返回页面,只是订单基本信息展示,不需要详细信息,所以提供一部分,就行了.
	//也就是提供一部分,页面上需要的数据,即 订单基本信息
	//为了以后 需要,以及未来扩展,需要查询全部数据,这样更加合适一点.
	
	Order order =new Order();
	order.setOrderId(orderId);
	QueryWrapper<Order> queryWrapper =new QueryWrapper<>(order);
	 order = orderMapper.selectOne(queryWrapper);
	//插入订单物流信息         插入订单商品列表信息
	 order.setOrderShipping(orderShipping).setOrderItems(orderItems);
	 
	System.out.println("======== 持久层打印:"+order.toString());
	return order;
}

@Override
public Order queryOrderByOrderIdMysql(String id) {
	//使用手写 SQL语句,实现查询order所有信息
	Order order=orderMapper.queryOrderByOrderId(id);
	//
	System.out.println("========MySQL 持久层打印:"+order.toString());

	
	return order;
}

	

}
