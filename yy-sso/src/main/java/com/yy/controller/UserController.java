package com.yy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yy.pojo.User;
import com.yy.service.UserService;
import com.yy.util.CookieUtils;
import com.yy.util.IPUtil;
import com.yy.vo.SysResult;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("findAll")
	public SysResult findAllUser() {
	List<User>	userList=userService.queryAll();
		
		return new SysResult(200, "服务请求成功!", userList);
		//http://sso.yy.com/user/findAll
		
	}
	
	

	@RequestMapping("check/{param}/{type}")
	public JSONPObject registerCheck(
			@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		//定义boolean 判断一个用户是否存在
	boolean flag=userService.findUserType(param,type);
		SysResult sysResult =SysResult.success(flag);
		
		return new JSONPObject(callback,sysResult);
		
	}
	
	/*****  用户数据回显   *****/
	/*****
	 * 	url : "http://sso.jt.com/user/query/" + _ticket,
	 * cookie
	 * 
	 * dataType : "jsonp",
			type : "GET",
			
			---
Request URL: http://sso.yy.com/user/query/bc8211b3-4cd6-4849-b631-b4e014657704?callback=jsonp1606734568671&_=1606734568746
			
callback: jsonp1606734568671
_: 1606734568746
	 * */
	
	@ResponseBody
	@RequestMapping("query/{ticket}/{username}")
	public JSONPObject queryByTicket(@PathVariable String ticket,@PathVariable String username,
			String callback,
			HttpServletRequest request,
			HttpServletResponse response) {
		//声明返回变量
		JSONPObject jsonObject =null;
		
		//根据username 从Redis中获取ticket的值
		String redisTicket = jedisCluster.get("YY_USER"+username);
		//如果查询为空,说明用户名错误. 不予展示用户信息
		if(redisTicket==null||!redisTicket.equals(ticket)) {
			//清除cookie, ----重新登
			//提前返回 空
			CookieUtils.delete("YY_TICKET", "/", "yy.com", response);
			CookieUtils.delete("YY_USER", "/", "yy.com", response);
			jsonObject =new JSONPObject(callback,SysResult.fail());
			//要把失败或者成功信息封装到 系统级 返回对象.
			return jsonObject;
		}
		//如果查询的ticket不一致,也不予展示
		
		
		//校验 通过ticket获取数据
		//根据ticket查询redis中的缓存,获取用户信息
		Map<String, String> map = jedisCluster.hgetAll(ticket);
		if(map ==null) {
			//清除cookie, ----重新登
			//提前返回 空
			CookieUtils.delete("YY_TICKET", "/", "yy.com", response);
			CookieUtils.delete("YY_USER", "/", "yy.com", response);
			jsonObject =new JSONPObject(callback,SysResult.fail());
			//要把失败或者成功信息封装到 系统级 返回对象.
			return jsonObject;
		}
		//map有值,下一步
		
		//0.校验IP是否正确 
		//AIP获取用户IP
		String ipAddr = IPUtil.getIpAddr(request);
		String userIP = map.get("YY_USER_IP");
		log.error("------------- >>>  IPUtil查询到的     实时IP:"+ipAddr);
		log.error("------------- >>>  Redis查询到的     存储IP:"+userIP);
		//错误,清除
		if(StringUtils.isEmpty(userIP)||!userIP.equals(ipAddr)) {
			//提前返回 空
			//清除cookie, ----重新登
			//提前返回 空
			CookieUtils.delete("YY_TICKET", "/", "yy.com", response);
			CookieUtils.delete("YY_USER", "/", "yy.com", response);
			jsonObject =new JSONPObject(callback,SysResult.fail());
			//要把失败或者成功信息封装到 系统级 返回对象.
			return jsonObject;
		}
		//正确,下一步
		
		//1.校验 ticket中的User是否有值
		String userJSON = map.get("YY_USER");
		if(StringUtils.isEmpty(userJSON)) {
			//清除cookie, ----重新登
			//提前返回 空
			CookieUtils.delete("YY_TICKET", "/", "yy.com", response);
			CookieUtils.delete("YY_USER", "/", "yy.com", response);
			jsonObject =new JSONPObject(callback,SysResult.fail());
			//要把失败或者成功信息封装到 系统级 返回对象.
			return jsonObject;
		}
		
		//正确,下一步
		//走到这一步 那么IP正确,ticket正确,需要返回User 了
		
		//转为 JSON对象就是为了 方便传输和解析,所以不要多此一举.
		//请求是JONSP请求那么返回也应该是符合JSONP要求.
		
	//	return new SysResult(200,callback, user);//需要把对象放入data当中
		SysResult sysResult =SysResult.success(userJSON);
		return new JSONPObject(callback, sysResult);//需要把对象放入data当中
	}
	
	
	

}
