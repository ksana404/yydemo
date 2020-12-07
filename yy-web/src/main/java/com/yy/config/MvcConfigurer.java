package com.yy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yy.interceptor.UserInterceptor;
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	//开启匹配后缀型配置  是的 .demo 和.demo.html效果一样
		@Override
		public void configurePathMatch(PathMatchConfigurer configurer) {
			
			configurer.setUseSuffixPatternMatch(true);
		}
		/******
		 * 
		 伪静态是相对真实静态来讲的，通常我们为了增强搜索引擎的友好面，都将文章内容生成静态页面，
		 但是有的朋友为了实时的显示一些信息。或者还想运用动态脚本解决一些问题。不能用静态的方式来展示网站内容。
		 但是这就损失了对搜索引擎的友好面。怎么样在两者之间找个中间方法呢，这就产生了伪静态技术。
		 伪静态技术是指展示出来的是以html一类的静态页面形式，但其实是用ASP一类的动态脚本来处理的。
		概括:以html结尾的动态页面.
		 */

		
		//定义拦截器  添加需要 匹配的路径
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			//userInterceptor
			registry.addInterceptor(userInterceptor).addPathPatterns("/cart/**","/order/**");
			//如果有多个拦截器,可以addInterceptor多次
			
		}
}
