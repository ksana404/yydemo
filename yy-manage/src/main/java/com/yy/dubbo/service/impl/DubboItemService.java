package com.yy.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yy.mapper.ItemDescMapper;
import com.yy.mapper.ItemMapper;
import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;

@Service
public class DubboItemService implements com.yy.service.DubboItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	

	@Override
	public Item queryItemById(Long itemId) {
		return itemMapper.selectById(itemId);
	}

	@Override
	public ItemDesc queryItemDescById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

}
