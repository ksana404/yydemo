package com.yy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.pojo.Cart;
import com.yy.pojo.User;
import com.yy.service.DubboCartService;
import com.yy.thread.UserThreadLocal;
import com.yy.vo.SysResult;

import io.swagger.annotations.Api;
@Api	//为当前类生成API
@Controller
@RequestMapping("/cart/")
public class CartController {
	
	@Reference
	private DubboCartService dubboCartService;
	
	
	/** http://www.yy.com/cart/show.html
Request Method: GET
Status Code: 404 

查询JSP页面,同时把数据放在页面内,需要借助 model
	 *
	 **/
	/**查询该用户的 购物车信息,并展示在页面上.
	 * 根据该用户的id查询,该用户的所有 购物车商品信息.
	 * @return
	 */
	@RequestMapping("show")
	public String catShow(Model model) {
		User user = UserThreadLocal.get();
		
		//暂时写死 用户ID,之后使用ThreadLcaol来存储用户基本信息
	//	Long userId=23L;
	List<Cart> cartList=dubboCartService.queryCartListByUserId(user.getId());
		
		model.addAttribute("cartList", cartList);
		return "cart"; //返回页面
		
	}
	
	/**用户 购物车商品-新增
	 * @param cart
	 * @return
	 */
	@RequestMapping("add/{itemId}")
	public String catAdd(Cart cart) {
		//暂时写死 用户ID,之后使用ThreadLcaol来存储用户基本信息
		//Long userId=23L;
		
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
		System.out.println("Controller  cart:"+cart.toString());
		dubboCartService.catAdd(cart);
	
		return "redirect:/cart/show.html"; //重定向到购物车商品列表页面
		
	}
	
	/** 用户 购物车商品-删除
	 * @param cart
	 * @return
	 */
	@RequestMapping("delete/{itemId}")
	public String catDelete(Cart cart) {
		//暂时写死 用户ID,之后使用ThreadLcaol来存储用户基本信息
		//Long userId=23L;
		
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
		dubboCartService.catDelete(cart);
	
		return "redirect:/cart/show.html"; //重定向到购物车商品列表页面
		
	}
	
	/** 用户 购物车商品-删除
	 * @param cart
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update/num/{itemId}/{num}")
	public SysResult catUpdate(Cart cart) {
		//暂时写死 用户ID,之后使用ThreadLcaol来存储用户基本信息
		//Long userId=23L;
		
		User user = UserThreadLocal.get();
		cart.setUserId(user.getId());
		//根据用户 ID ,商品ID,商品数量 num 来更新商品数量.
		dubboCartService.catUpdate(cart);
	
		return SysResult.success();
	}

}



















