package com.yy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DemoInterceptor implements HandlerInterceptor{
	/**
	 * 拦截器是介于 用户和服务器之间.
	 * 
	 * Handler 就是处理器,在SpingMVC之中,获取请求之后就需要 Handler 来处理,业务处理.
	 * 
	 * SpingMVC 最后一步是视图渲染, afterCompletion就是视图渲染结束后,离用户最后一步所做的事情.
	 * 
	 * 总结起来:
	 * preHandle 请求处理 前一步
	 * postHandle 请求处理 后一步
	 * 
	 * afterCompletion 整个请求走完最后一步.(前面是视图渲染,后一步是用户了)
	 * 
	 * 
	 * 
	 * */
	
	//请求之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	//请求之后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	
	//方法全部完成后 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
