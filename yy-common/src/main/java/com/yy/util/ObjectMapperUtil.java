package com.yy.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//加载类就可以了,不需要对象,也就不需要Bean
public class ObjectMapperUtil {
	
	//1.声明ObjectMapper对象.
		private static final ObjectMapper mapper =new ObjectMapper();
		
		//1. 对象转化为Json  把对象写成json格式
		//如果有异常,需要转化成运行期异常
		//使用 静态方法是因为 工具类大家的方法都是一套,不需要变动,所以就是用静态的更加方便.
		// 
		/**
		 * @param obj 参数是需要转化的对象
		 * @return  json  返回值是转化好的json格式,以String形式存在.
		 */
		public static String toJson(Object obj) {
			String json;
			
			try {
				
				json = mapper.writeValueAsString(obj);
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			return json;
			
		}
		
		
		//2.Json转化成对象,需要对象类型,使用到泛型
		//把JSON按照目标类型 读出来
		//参数 需要Json字符串,转化的目标类型
		/**
		 * @param <T>    ? 泛型修饰返回对象
		 * @param json   目标json串
		 * @param targetClass   json需要转化的目标对象类型 
		 * @return t  返回的目标对象
		 */
		public static <T> T toObject(String json,Class<T> targetClass) {
			T t=null;
			
			
				try {
					 t = mapper.readValue(json, targetClass);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
	
			return t;
			
			
		}
		
		
		

}
