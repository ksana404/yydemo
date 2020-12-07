package com.yy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yy.pojo.Cart;
import com.yy.pojo.Order;
import com.yy.service.DubboCartService;
import com.yy.service.DubboOrderService;
import com.yy.thread.UserThreadLocal;
import com.yy.vo.SysResult;

import io.swagger.annotations.Api;
@Api	//为当前类生成API
@Controller
@RequestMapping("/order/")
public class OrderController {
	
	@Reference(check = false)  //启动时,不检查服务是否已经提供.  注意使用Dubbo远程调用,一定要使用 这个注解.
	private DubboOrderService dubboOrderService;
	
	@Reference(check = false)  //启动时,不检查服务是否已经提供.  注意使用Dubbo远程调用,一定要使用 这个注解.
	private DubboCartService dubboCartService;
	//DubboCartService
	
	/** http://www.yy.com/order/create.html
	 * 1.因为是获取页面,所以返回页面路径,返回值 String
	 * 2.因为是jsp页面,输入放入需要 Model参数.
	 * import org.springframework.ui.Model;
	 * 
	 * 3.页面返回,不要添加 @ResponseBody,切记！
	 * 
	 * 4.生成订单,是把用户购物车里面的东西全部生成订单.
	 * 所需要的信息有  1.用户ID 
	 * 然后通过用户ID,查询用户购物车中所有 商品列表信息,统一生成订单.
	 * */
	
	@RequestMapping("create")
	public String createOrder(Model model) {
		Long userId = UserThreadLocal.get().getId();
	//Order order=dubboOrderService.createOrder(userId);
		//根据用户ID查询 用户所有的购物车商品信息
	List<Cart> cartList=dubboCartService.queryCartListByUserId(userId);
	model.addAttribute("carts", cartList);
		
		return "order-cart";
	}
	
	@RequestMapping("submit")
	@ResponseBody
	public SysResult submitOrder(Order order) {
		Long userId = UserThreadLocal.get().getId();
		order.setUserId(userId);
		//提交之后应该生成 订单id,返回
		//通过 用户提交的订单信息  新增订单.(购物车信息,和 用户填写的物流信息)
		String orderId =dubboOrderService.addOrder(order);
		//判断是否 新增 订单成功!
		if(StringUtils.isEmpty(orderId)) {
			return SysResult.fail();
		}
		
		return SysResult.success(orderId);
	}
	
	//订单新增 成功返显
	/**
	 * http://www.yy.com/order/success.html?id=246253
	 *问号后面 是参数,不需要写在URL里面()  --@RequestMapping("success")
Request Method: GET
Status Code: 404 
	 * */
	@RequestMapping("success")
	public String successOrder(Model model,String id) {
		//根据订单ID查询 orderId 订单信息,在成功页面上显示
		//System.out.println("控制层========== ============ id"+id);
		//Order order=dubboOrderService.queryOrderByOrderId(id);
		Order order=dubboOrderService.queryOrderByOrderIdMysql(id);
		
		System.out.println("控制层========== ============ order"+order.toString());
	model.addAttribute("order", order);
		
		return "success";
	}

}
