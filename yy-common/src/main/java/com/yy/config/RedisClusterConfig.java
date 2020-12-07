package com.yy.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

//引入配置类标识
@Configuration
//指引引入文件的路径
@PropertySource("classpath:/properties/redis.prpperties")
public class RedisClusterConfig {
	
	@Value("${redis.nodes}") //使用springframework的注解
	private String nodes;
	/**
	 * redis.nodes=192.168.64.140:7001,192.168.64.140:7002,192.168.64.140:7003,192.168.64.140:7004,192.168.64.140:7005,192.168.64.140:7006,192.168.64.140:7007,192.168.64.140:7008,192.168.64.140:7009,192.168.64.140:7010,192.168.64.140:7011,192.168.64.140:7012


	 * */
	
	/********
 @Scope注解是springIoc容器中的一个作用域，在 Spring IoC 容器中具有以下几种作用域：
 基本作用域singleton（单例）、prototype(多例)，Web 作用域（reqeust、session、globalsession），自定义作用域

a.singleton单例模式 – 全局有且仅有一个实例
b.prototype原型模式 – 每次获取Bean的时候会有一个新的实例
c.request – request表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
d.session – session作用域表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
e.globalsession – global session作用域类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义
	 * 
	 * */
	
	@Scope("prototype")  //
	@Bean
	public JedisCluster jedisGet() {
		
		//0.声明 创建 JedisCluster 的参数,Set集合,元素包含host与port,每个元素就是一个node节点.
		Set<HostAndPort> nodesSet =new  HashSet<>();
		/** private String host;
  			private int port;
		 * */
		//1.对配置文件的信息进行切割
		String[] splitArray = nodes.split(",");
		for(String nodeStr:splitArray) {
			String host = nodeStr.split(":")[0];
			int port = Integer.parseInt(nodeStr.split(":")[1]);
			HostAndPort note =new HostAndPort(host, port);
			nodesSet.add(note);
		}
		
		return new JedisCluster(nodesSet);
		
	}
/**
 * # 注意 每个模块需要单独放置这个配置文件,因为Spring容器加载不到通用层的配置文件
 * 
 * 问题:请问@Bean注解.获取对象返回值放入容器,这个对象的引用变量 是默认为方法名,还是返回值类型首字母小写?
 * 默认使用方法名?
 * */
}
