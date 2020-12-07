package com.yy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yy.pojo.ItemDesc;
import com.yy.service.ItemService;
import com.yy.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	
	@Autowired
	private ItemService itemService;
	
	/**
http://manage.yy.com/web/testJSONP?callback=hello&_=1606556445477
callback: hello
_: 1606556445477

返回参数:callback
返回值类型:jsonp
	 * */
	//@RequestMapping("/web/testJSONP")
	public String testJSONP(String callback) {
		ItemDesc itemDesc =new ItemDesc();
		itemDesc.setItemId(996887L);
		itemDesc.setItemDesc("kong kong kong");
		String json = ObjectMapperUtil.toJson(itemDesc);
		return callback+"("+json+")";
		
	}
	
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONPObject(String callback) {
		ItemDesc itemDesc =new ItemDesc();
		itemDesc.setItemId(996887L);
		itemDesc.setItemDesc("dong dong dong");
		return new JSONPObject(callback, itemDesc);
		
	}

	
	
	
	
}
