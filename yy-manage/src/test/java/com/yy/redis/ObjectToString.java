package com.yy.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.pojo.ItemDesc;

public class ObjectToString {
	
	//1.声明ObjectMapper对象.  直接new出来就不需要注入了.
	private static final ObjectMapper mapper =new ObjectMapper();
	
	//单个对象
	@Test
	public void toObjectJsonTest() throws JsonProcessingException{
		//2.声明要转化的对象
		ItemDesc itemDesc =new ItemDesc();
		itemDesc.setItemId(11570L)
		.setItemDesc("我是商品详细信息").setCreated(new Date()).setUpdated(new Date());
		
		//3.利用 ObjectMapper 把目标对象转化为Json以String形式存在
		//把对象 写成 Json串
		String jsonStr = mapper.writeValueAsString(itemDesc);
		System.out.println("jsonStr:"+jsonStr);
		
		
		//4.利用 ObjectMapper 把 json 目标转化为对象,需要对象的类型参数
		//把Json串 以某个对象为模板 读出来.
		ItemDesc readValue;
		
		try {
			readValue = mapper.readValue(jsonStr, ItemDesc.class);
			System.out.println("readValue:"+readValue.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
	//像这种不需要容器注入的,直接New出来更加方便,快捷.
	//private ObjectMapperUtil objectMapperUtil=new ObjectMapperUtil();
	
	//list存放对象
	@SuppressWarnings("unchecked")
	@Test
	public void toObjectJsonListTest() throws JsonProcessingException {
		//1.声明要转化的对象
		List<ItemDesc> list =new ArrayList<ItemDesc>();
		
		
		ItemDesc itemDesc =new ItemDesc();
		itemDesc.setItemId(11570L)
		.setItemDesc("我是商品详细信息").setCreated(new Date()).setUpdated(new Date());
		
		ItemDesc itemDesc01 =new ItemDesc();
		itemDesc01.setItemId(21570L)
		.setItemDesc("不是啦,商品信息").setCreated(new Date()).setUpdated(new Date());
		
		ItemDesc itemDesc02 =new ItemDesc();
		itemDesc02.setItemId(31570L)
		.setItemDesc("你猜 商品信息").setCreated(new Date()).setUpdated(new Date());
		
		list.add(itemDesc);
		list.add(itemDesc01);
		list.add(itemDesc02);
		
	
		
		//3.利用 ObjectMapper 把目标对象转化为Json以String形式存在
		//把对象 写成 Json串
		String jsonStr = mapper.writeValueAsString(list);
		System.out.println("jsonStr:"+jsonStr);
		
		
		//4.利用 ObjectMapper 把 json 目标转化为对象,需要对象的类型参数
		//把Json串 以某个对象为模板 读出来.
		  List<ItemDesc> readValue;
		try {
			readValue = mapper.readValue(jsonStr,list.getClass());
			System.out.println("readValue:"+readValue.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		  

		
		
		System.out.println("===================");
		//因为是静态方法可以直接使用 类来访问.而这个类本身是一个公开的类,公开的方法.
		//这里加载的时候会去寻找该包中的类,加载进来,再调用方法
		String json = ObjectMapperUtil.toJson(list);
		System.out.println("-------json:"+json);
		
		List<ItemDesc> objectList = ObjectMapperUtil.toObject(json, list.getClass());
		 System.out.println("-------objectList:"+objectList.toString());
	}

	//list存放对象
		@SuppressWarnings("unchecked")
		@Test
		public void objectMapperUtilTest() {
			//1.声明要转化的对象
			List<ItemDesc> list =new ArrayList<ItemDesc>();
			
			
			ItemDesc itemDesc =new ItemDesc();
			itemDesc.setItemId(77711570L)
			.setItemDesc("我是商品详细信息").setCreated(new Date()).setUpdated(new Date());
			
			ItemDesc itemDesc01 =new ItemDesc();
			itemDesc01.setItemId(77721570L)
			.setItemDesc("不是啦,商品信息").setCreated(new Date()).setUpdated(new Date());
			
			ItemDesc itemDesc02 =new ItemDesc();
			itemDesc02.setItemId(77731570L)
			.setItemDesc("你猜 商品信息").setCreated(new Date()).setUpdated(new Date());
			
			list.add(itemDesc);
			list.add(itemDesc01);
			list.add(itemDesc02);
		
			
			System.out.println("===================");
			//因为是静态方法可以直接使用 类来访问.而这个类本身是一个公开的类,公开的方法.
			//这里加载的时候会去寻找该包中的类,加载进来,再调用方法
			String json = ObjectMapperUtil.toJson(list);
			System.out.println("-------json:"+json);
			
			List<ItemDesc> objectList = ObjectMapperUtil.toObject(json, list.getClass());
			 System.out.println("-------objectList:"+objectList.toString());
		}

}
