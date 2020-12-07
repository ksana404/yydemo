package com.yy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {
	
	@Value("${server.port}")
	private Integer port;
/**
 * 1.说明获取 配置文件的参数,可以通过 @Value("${server.port}") 直接获取 application.yml上面的参数
 * 是以 key-value 形式存在,而且直接注入到 字段当中,private Integer port;.
 * 
 * 2.获取其他文件的参数 需要在类上面添加 获取文件的路径.
 * @PropertySource("classpath:/properties/image.prpperties")
 * 
 * */
	
	@RequestMapping("/getPort")
	public String getPort() {
		return "端口号为:"+port;
	}

}
