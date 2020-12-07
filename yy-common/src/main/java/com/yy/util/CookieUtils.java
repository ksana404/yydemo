package com.yy.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

/**Cookie 操作工具类
 * @author Administrator
 *
 */
public class CookieUtils {
	/****
	 * 工具类里面的方法,大家使用的都是同一份,所以使用静态方法,static修饰
	 * 因为需要大家方便使用,所以使用public修饰.
	 * @return 
	 * **/
	//删除Cookie,根据Cookie名字删除.  实际上是进行覆盖.
	//删除Cookie方法,需要 Cookie名字,HttpServletResponse.
	public static void delete(String cookieName,String path,String doMain,HttpServletResponse response) {
		//1.新建Cookie
		Cookie cookie = new Cookie(cookieName,"");
		//2.时间归零,使其失效
		cookie.setMaxAge(0); //
		//3.其他属性设置为一致
		cookie.setPath(path);		//设定cookie读取范围
		cookie.setDomain(doMain); //设定cookie共享范围
		
		//4. 最重要一步
		response.addCookie(cookie);
	}
	
	
	//获取Cookie的值,根据Cookie名字获取
	public static String getValue(String cookieName,HttpServletRequest request) {
		//1.从浏览器获取所有Cookie
		Cookie[] cookies = request.getCookies();
		//1.5判断
		if(cookies!=null&&cookies.length>0) {
			//2.遍历  根据cookieName 找到 value
			for(Cookie cookie:cookies) {
				String name = cookie.getName();
				if(!StringUtils.isEmpty(name)&& name.equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		
		return null;
		
	}

}
