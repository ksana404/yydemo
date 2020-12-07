package com.yy.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yy.pojo.User;
import com.yy.thread.UserThreadLocal;
import com.yy.util.CookieUtils;
import com.yy.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

/** 登录拦截器
 * @author Administrator
 *
 */

@Component
public class UserInterceptor implements HandlerInterceptor{
	
	@Autowired
	private  JedisCluster jedisCluster;
	
	/*实现用户权限认证
	 * 1.用户不登录,不允许访问涉密操作.重定向到登录页面.
	 * 
	 * 2.如果用户登录后,则允许放行.
	 * 
	 * 注意: 接口中 default 修饰的方法,可以不重写
	 * 
	 * */
	
	/**方法说明:
	 * 1.boolean 
	 * 		true:放行
	 * 		false:拦截,一般配合重定向使用.不然程序会卡死.  重定向到另一条路.
	 *
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
	
		
		
		//拦截所有涉密操作   这个需要使用 WebMvcConfigurer 来规定拦截哪些请求
		//通过 路径的截获,来实现拦截所有涉密操作
		
		//判断是否登录 从request中查询Cookie中的信息.,非空,一致性校验.
		//(同时把数据入ThreadLocal中)
		//登录,放行
		//没有登录,重定向到登录页面.
		
		//如果用户登录了,那么用户的Cookie中会携带登录信息..    YY_USER,YY_TICKET
		//如果验证登陆了就放行
		//使用WebMvcConfigurer已经完成了涉密操作拦截,所以这里必须登录.  只有涉密操作超会被拦截到这里
		//获取Cookie数据
		String userNameCookie = CookieUtils.getValue("YY_USER", request);
		String tickeCookie = CookieUtils.getValue("YY_TICKET", request);
		if(StringUtils.isEmpty(userNameCookie)||StringUtils.isEmpty(tickeCookie)) {
			
			response.sendRedirect("/user/login.html");
			return false;
		}
		
		//获取Redis数据
		String ticketRedis = jedisCluster.get("YY_USER"+userNameCookie);
		//进一步校验
		if(!StringUtils.isEmpty(ticketRedis)&&tickeCookie.equals(ticketRedis)) {
			//当Redis中tikect 也与Cookie中一致的时候
			Map<String, String>  hgetAll= jedisCluster.hgetAll(ticketRedis);
			if(hgetAll!=null) {
				//已经登录
				String userJson = hgetAll.get("YY_USER");
				
				User user = ObjectMapperUtil.toObject(userJson, User.class);
				//把数据放入线程 空间
				UserThreadLocal.set(user);
				//顺利通过
				return true;
			}
		}
		
		
		response.sendRedirect("/user/login.html");
		return false;
	}
	
@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {

	UserThreadLocal.remove();
}
	
	

}
