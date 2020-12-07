package com.yy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import redis.clients.jedis.Jedis;

//引入配置类标识
//@Configuration
//指引引入文件的路径
//@PropertySource("classpath:/properties/redis.prpperties")
public class RedisConfig {
	
	@Value("${redis.host}") //使用springframework的注解
	private String host;
	@Value("${redis.port}") //使用springframework的注解
	private Integer port;
	
	@Bean
	public Jedis jedisGet() {
		return new Jedis(host,port);
		
	}
/**
 * # 注意 每个模块需要单独放置这个配置文件,因为Spring容器加载不到通用层的配置文件
 * 
 * 问题:请问@Bean注解.获取对象返回值放入容器,这个对象的引用变量 是默认为方法名,还是返回值类型首字母小写?
 * 默认使用方法名?
 * */
}
