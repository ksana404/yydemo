package com.yy.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.pojo.Item;

@SpringBootTest
public class ItemMapperTest {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Test
	public void findAllItems() {
		//1.使用原来的方法
		List<Item> findAllItems = itemMapper.findAllItems();
		System.out.println("=========>"+findAllItems.toString());
		
		//2 使用mybatis-Plus方法
		QueryWrapper<Item> queryWrapper =new QueryWrapper();
		List<Item> selectList = itemMapper.selectList(queryWrapper);
		System.out.println("mybatis-Plus方法=========>"+selectList.toString());
		
	}

}
