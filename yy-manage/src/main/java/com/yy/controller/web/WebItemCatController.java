package com.yy.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yy.pojo.ItemCat;
import com.yy.service.ItemCatService;
import com.yy.vo.SysResult;

@RestController  //接受web使用JSONP跨域访问
@RequestMapping("/web/itemcat/")
public class WebItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 *Request URL: 
http://manage.yy.com/web/itemcat/all?callback=category.getDataService

Request Method: GET
Status Code: 404 

callback: category.getDataService 
1.需要知道返回数据的结构
2.请求的路径,请求参数.
	 * 
	 * */
	//@RequestMapping("all")
	public JSONPObject queryItemCatAll(String callback) {
	//
		
	List<ItemCat>	itemCatList=itemCatService.queryItemCatAll();
	
		SysResult sysResult =SysResult.success(itemCatList);
		
		return new JSONPObject(callback,sysResult);
		
	}
	

}
