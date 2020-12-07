package com.yy.aop;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yy.vo.SysResult;

/**
 * 由于jsonp的请求方式,返回值必须callback(json);
 * 思路:利用request对象动态获取callback
 * 	如果用户参数是get请求,并且参数名称为callback
 * 	则使用jsonp的方式返回数据
 * 	如果请求中没有callback则按照规则正常返回!!!!
 * 
 * @param e
 * @return
 */

public class SystemExceptionAOP {

	@ExceptionHandler(RuntimeException.class)
	public Object fail(RuntimeException e,HttpServletRequest request) {
		e.printStackTrace();
		String callback = request.getParameter("callback");
		if(StringUtils.isEmpty(callback)) {
			return SysResult.fail();
		}else {
			//采用jsonp的方式调用服务器
			return new JSONPObject(callback, SysResult.fail());
		}
	}
	
}
