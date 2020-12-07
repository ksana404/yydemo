package com.yy.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.pojo.Order;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {
	
	@Autowired
	private OrderMapper orderMapper; //订单基本表  持久层接口

	@Test
	public void selectByIdTest(){
		QueryWrapper<Order> qw =new QueryWrapper<>();
		qw.eq("order_id", "246253");
		Order selectOne = orderMapper.selectOne(qw);
	//	Order selectById = orderMapper.selectById(246253);
		System.out.println("selectOne:"+selectOne.toString());
//		@SpringBootTest
//		@RunWith(SpringRunner.class)
		//测试需要停掉模块中的Spring容器,不然会占用 服务提供者的端口号.
		//因为测试 也会启动一台临时的 Spring容器.
	}
	
	
	@Test
	public void selectByIdTest01() {
		String orderId ="246253";
		Order order =new Order();
		order.setOrderId(orderId);
		QueryWrapper<Order> queryWrapper =new QueryWrapper<>(order);
		 order = orderMapper.selectOne(queryWrapper);
		
		System.out.println("======== 持久层打印:"+order.toString());
	}
	
	@Test
	public void queryOrderByOrderIdTest() {
		String orderId ="246253";
	
	Order order = orderMapper.queryOrderByOrderId(orderId);
		
		System.out.println("======== 持久层打印:"+order.toString());
	}
}
