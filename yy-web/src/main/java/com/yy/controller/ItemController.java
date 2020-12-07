package com.yy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;
import com.yy.service.DubboItemService;
import com.yy.service.ItemService;

import io.swagger.annotations.Api;
@Api	//为当前类生成API
@Controller
public class ItemController {
	
	@Autowired
	private ItemService  itemService;
	
	@Reference
	private DubboItemService  dubboItemService;
	
	
	
	/**
	 * http://www.yy.com/items/562379.html
	 * */
	
	//@RequestMapping("/items/{itemId}")
	/*
	 * public String queryItemByIdOld(@PathVariable Long itemId,Model model) {
	 * 
	 * Item item= itemService.queryItemById(itemId); ItemDesc itemDesc=
	 * itemService.queryItemDescById(itemId);
	 * System.out.println("================== 商品打印:"+item.toString());
	 * System.out.println("================== 商品详情打印:"+itemDesc.toString());
	 * model.addAttribute("item", item); //item model.addAttribute("itemDesc",
	 * itemDesc);
	 * 
	 * return "item"; }
	 */

	/********      使用Dubbo进行远程调用       *********/
	/**
	 * http://www.yy.com/items/562379.html
	 * */
	@RequestMapping("/items/{itemId}")
	public String queryItemById(@PathVariable Long itemId,Model model) {
		// dubboItemService
		Item item=	dubboItemService.queryItemById(itemId);
		ItemDesc itemDesc= dubboItemService.queryItemDescById(itemId);
		System.out.println("================== 商品打印:"+item.toString());
		System.out.println("================== 商品详情打印:"+itemDesc.toString());
		model.addAttribute("item", item); //item
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";
	}
	
}
