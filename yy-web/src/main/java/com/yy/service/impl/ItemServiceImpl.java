package com.yy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;
import com.yy.service.HttpClientService;
import com.yy.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private HttpClientService httpClientService;
	
	@Override
	public Item queryItemById(Long itemId) {
		String url="http://manage.yy.com/web/item/findItemById?itemId="+itemId;
		
		return httpClientService.doGet(url, Item.class);
	}

	@Override
	public ItemDesc queryItemDescById(Long itemId) {
		String url="http://manage.yy.com/web/item/findItemDescById?itemId="+itemId;
		
		return httpClientService.doGet(url, ItemDesc.class);
	}

}
