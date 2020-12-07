package com.yy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;
import com.yy.service.ItemService;
import com.yy.util.ObjectMapperUtil;
import com.yy.vo.SysResult;

@RequestMapping("/web/")  //这是接受来自web请求的控制类
@RestController
public class WebController {
	@Autowired
	private ItemService itemService;
	
	/****
	 * 
	 * url:http://manage.yy.com/web(服务器信息)/item(业务信息)/findItemById(请求方法)?
	 * itemId=xxxxx
	 * */
	
	@RequestMapping("item/findItemById")
	public SysResult webFindItem(Long itemId) {
		//@PathVariable 注解要求 名字,类型 严格一致.
		//而普通的会进行一定的转换,SpringMVC框架自带
		
		Item item=itemService.queryItem(itemId);
		String json = ObjectMapperUtil.toJson(item);
		
		return new SysResult(200,"服务器处理成功!!", json);
	}
	
	/**
 url:http://manage.yy.com/web/item/findItemDescById?itemId=xxxxx
要求:动态查询商品详情信息.之后json返回
	 * */
	
	@RequestMapping("item/findItemDescById")
	public SysResult webFindItemDesc(Long itemId) {
		
		ItemDesc queryItemDesc = itemService.queryItemDesc(itemId);
		String json = ObjectMapperUtil.toJson(queryItemDesc);
		return new SysResult(200,"服务器处理成功!!", json);
	}
	
	

	
	
	
}
