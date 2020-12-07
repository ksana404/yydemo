package com.yy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** 通用页面跳转的 控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {
	/**
	 * 实现通用页面跳转 restFul风格
	 * 
	 * url:/page/item-add
	 * url:/page/item-list
	 * url:/page/item-param-list
	 * 特点说明:
	 * 	前缀都是相同的,后缀与页面名称相同
	 * 
	 * 思路:
	 * 	如何动态获取url中的参数呢?
	 * 
	 * 语法步骤:
	 * 	1.使用{}将url中的参数包裹,并且参数之间使用/分割
	 *  2.参数名称必须和url中的名称一致
	 *  3.@PathVariable添加该注解,实现数据的转化
	 *  
	 *  restFUl(二)
	 *  可以指定请求类型,实现url通用
	 *  
	 *  url地址: /user
	 *  类型: 
	 *  	1. get   查询操作
	 *   	2. post  新增操作
	 *   	3. put	 修改操作
	 *   	4. delete 删除操作
	 */
	
	@RequestMapping(value="page/{moduleName}")
	public String toModule(@PathVariable String moduleName) {
		return moduleName;
	}
	

}
