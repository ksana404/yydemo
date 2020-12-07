package com.yy.dubbo.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.mapper.UserMapper;
import com.yy.pojo.User;
import com.yy.service.DubboUserService;
import com.yy.util.CookieUtils;
import com.yy.util.ObjectMapperUtil;
import com.yy.vo.SysResult;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Service
public class DubboUserServiceImpl implements DubboUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public void doRegister(User user) {
		//0.密码加密
		String md5Pwy = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		//1.数据准备
		user.setPassword(md5Pwy).setCreated(new Date()).setUpdated(user.getCreated());
		//2.数据插入
		userMapper.insert(user);
		
	}

	@Override
	public String findUserUP(User user,String userIP) {
		
		
		
		//为了使得用户访问在同一个ip地址下面,所以我们需要对用户ip存入 Redis中.
		
		//0.参数处理 这时候用户和密码是明文的,数据库里面的密码是加密的,为了匹配
		//需要把用户提交的密码 加密然后再和 数据库的比较.条件查询.
		String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pwd);
		//1.条件构造器
		QueryWrapper<User> qw =new QueryWrapper<>(user);
		
		//2.执行sql 条件查询
		User selectOne = userMapper.selectOne(qw);
		
		//3.判断查询到有值,说明 用户填写的密码和用户名正确,则生成 ticket
		//如果不正确,则返回null
		if(selectOne ==null) {
			return null;
		}
		
		//4.到这一步,说明用户名和密码正确
		/*************        同时登录校验          **************/
		String preTicket = jedisCluster.get("YY_USER"+user.getUsername());
		if(!StringUtils.isEmpty(preTicket)) {
			//需要先退出.
			return "exit";
		}
		//为空 则说明还没有登录
		
		//4.1 生成 ticket
		String ticket = UUID.randomUUID().toString();
		
		//4.2准备userJSON数据,
		//需要对其进行脱敏处理,然后存入 redis.
		selectOne.setPassword("123456");
		String userJSON = ObjectMapperUtil.toJson(selectOne);
		jedisCluster.hset(ticket, "YY_USER", userJSON);
		jedisCluster.hset(ticket, "YY_USER_IP", userIP);
		
		jedisCluster.expire(ticket, 7*24*60);
		
		//将usernmae和ticket绑定起来
		jedisCluster.setex("YY_USER"+user.getUsername(), 7*24*60, ticket);
		
		return ticket;
	}

	

}
