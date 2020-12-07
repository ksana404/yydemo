package com.yy.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.pojo.User;
import com.yy.service.DubboUserService;
import com.yy.util.CookieUtils;
import com.yy.util.IPUtil;
import com.yy.vo.SysResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Api	//为当前类生成API
@Slf4j
@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Reference(check = false,timeout = 3000) //阿里巴巴的Dubbo 远程接口注入容器 使用这个注解
	private DubboUserService dubboUserService;
	
	@Autowired
	private JedisCluster jedisCluster;

	
//
//	@Autowired //
//	private UserService userService;
//	
/**
 @GetMapping("/findAll")
	@ApiOperation(value = "查询用户全部信息",notes ="你TM是小学文化吗,这都读不懂???")
 * */	
	
	@GetMapping(value="/{moduleName}")
	@ApiOperation(value = "页面跳转路径",notes ="直接从项目的静态资源下获取页面")
	@ApiResponses({@ApiResponse(code = 401,message = "用户操作服务器时,用户没有认证"),
		@ApiResponse(code = 403,message = "服务响应异常!!!"),
		@ApiResponse(code = 404,message = "路径异常!!!!")
		})
	public String toModule(@PathVariable String moduleName) {
		return moduleName;
	}
	
	

	@ResponseBody
	@PostMapping("doRegister")
	@ApiOperation(value = "用户输入注册所需信息",notes ="信息会自动封装到User参数里面去")
	@ApiResponses({@ApiResponse(code = 401,message = "用户操作服务器时,用户没有认证"),
		@ApiResponse(code = 403,message = "服务响应异常!!!"),
		@ApiResponse(code = 404,message = "路径异常!!!!")})
	public SysResult doRegister(User user) {
		dubboUserService.doRegister(user);
		return SysResult.success("成功注册!");
	}
	
	
	/*******     第一次登录          ********/
	/**
	 * 0.需要参数是 username和password.同时需要 request传递登录用户的id,和返回 response携带cookie.
	 * cookie中有 ticke的信息,将要存储在用户的浏览器那里. 以便用户再次登录的时候,携带ticke信息,完成登录校验.
	 * (User user,HttpServletResponse  response,HttpServletRequest request)
	 * 
	 * 1.
	 * 2.
	 * 3.
	 * 
	 * 
	 1.准备Cookie对象
	 * 
	 * cookie.setMaxAge(expiry);
	 * expiry>0   为cookie设定超时时间单位秒
	 * expiry=0   删除Cookie
	 * expiry=-1 cookie会话关闭之后删除.
 	 * 
 	 * cookie.setPath("/"); 设定cookie的作用范围
 	 * 因为返回需要携带Cookie,所以需要添加 HttpServletResponse  response  
	 * @param user
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value = "用户输入登录所需信息",notes ="信息会自动封装到User参数里面去")
	@ApiResponses({@ApiResponse(code = 401,message = "用户操作服务器时,用户没有认证"),
		@ApiResponse(code = 403,message = "服务响应异常!!!"),
		@ApiResponse(code = 404,message = "路径异常!!!!")})
	@PostMapping("doLogin")
	public SysResult doLogin(User user,HttpServletResponse  response,HttpServletRequest request) {
		
		
				
		//0.		
		//获取userIP
		String userIP = IPUtil.getIpAddr(request);
		log.error("用户访问的IP为:"+userIP);
		
		//0.根据用户名和密码,查询 数据库,用户是否存在.如果存在返回一个 ticket,如果不存在则返回Null
		String ticket= dubboUserService.findUserUP(user,userIP);
		
		//1.判断 ticket,如果不存在.则不允许登录.如果存在,则Cookie返回ticket.
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.fail(); //如果错误,则返回失败.
		}
		/************         同时登录校验           **************/
		//---- 如果是用户已经登录,先进行校验. 需要先退出,再登录.  //这个校验应该在你的用户名和密码都正确的前提下.
		if("exit".equals(ticket)) {
			//需要先退出.
			return SysResult.fail("请先退出,再登录.");
		}
		
		//到此时,说明,ticket不为空也不为exit.  可以重置Cookie
		Cookie ticketCookie = new Cookie("YY_TICKET",ticket);
		ticketCookie.setMaxAge(7*24*60*60); //7天有效
		ticketCookie.setPath("/");		//设定cookie读取范围
		ticketCookie.setDomain("yy.com"); //设定cookie共享范围
		response.addCookie(ticketCookie);
		
		//将username的信息写入cookie
		Cookie userNameCookie = new Cookie("YY_USER",user.getUsername());
		userNameCookie.setMaxAge(7*24*60*60); //7天有效
		userNameCookie.setPath("/");		//设定cookie读取范围
		userNameCookie.setDomain("yy.com"); //设定cookie共享范围
		response.addCookie(userNameCookie);
		
		
		//dubboUserService.doLogin(user);
		return SysResult.success(); //正确返回
	}
	
	/**
	 * http://www.yy.com/user/logout.html
	 * 清除数据 需要cookie中携带ticket数据,找到对应的redis 的key清除
	 * 同时需要 重置cookie中的ticket数据.
	 * 所以需要  HttpServletResponse  response,HttpServletRequest request
	 * @param response
	 * @param request
	 * @return
	 */
	
	@ApiOperation(value = "用户退出登录",notes ="退出登录,会清空浏览器和Redis中用户登录的数据")
	@ApiResponses({@ApiResponse(code = 401,message = "用户操作服务器时,用户没有认证"),
		@ApiResponse(code = 403,message = "服务响应异常!!!"),
		@ApiResponse(code = 404,message = "路径异常!!!!")})
	@GetMapping("logout")
	public String logout(HttpServletResponse  response,HttpServletRequest request) {
		
				//0.从cookie中获取ticket数据
				String ticket = CookieUtils.getValue("YY_TICKET", request);
				String username = CookieUtils.getValue("YY_USER", request);
				//1.校验 清除redis
				if(!StringUtils.isEmpty(ticket)||!StringUtils.isEmpty(username)) {
					//
					//如果还有数据则清除
					Map<String, String> hgetAll = jedisCluster.hgetAll(ticket);
					String redisTicket = jedisCluster.get("YY_USER"+username);
					if(hgetAll!=null||!StringUtils.isEmpty(redisTicket)) {
						
						Long del = jedisCluster.del(ticket);
						Long delname = jedisCluster.del("YY_USER"+username);
						log.error("清除Redis中的 ticket 数据:"+del+"被清除的为:"+ticket);
						log.error("清除Redis中的 userName 数据:"+delname+"被清除的为:"+username);
						//2 
						//后面再重置 Cookie
						CookieUtils.delete("YY_TICKET", "/", "yy.com", response);
						CookieUtils.delete("YY_USER", "/", "yy.com", response);
						log.error("-------- ---------- >>>>退出登录,成功!");
					}
				}
		
				return "redirect:/"; 
	}
	
	

}
