package com.yy.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.mapper.UserMapper;
import com.yy.pojo.User;
import com.yy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> queryAll() {
		return userMapper.selectList(null);
	}

	/**
	  参数说明:
	 * 	1.param  需要校验的数据
	 *  2.type   1 username、2 phone、3 email
	 * 
	 * 业务说明:根据参数查询数据库
	 * 	sql: select * from tb_user  字段 = #{param}
	 * 
	 * 结果返回: 如果存在返回true  不存在返回 false
	 */
	@Override
	public boolean findUserType(String param, Integer type) {
/**
 * 分析:既然 type 和数字是对应的,那么使用kv形式存储起来MAP,然后使用mybatis 的条件 字段colum查询,
 * 条件字段的值为传入的 param值.
 *  colum 来自于Map.
 *  
 *  查询
 * */
		//1.使用Map进行 type对应数据 封装
		HashMap<Integer,String> map =new HashMap<>();
		map.put(1, "username");
		map.put(2, "phone");
		map.put(3, "email");
		//2.获取数据类型
		String colum =map.get(type);
		//3.声明QW
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();

		//4.条件封装
		queryWrapper.eq(colum, param);
		//5.数据查询
		User selectOne = userMapper.selectOne(queryWrapper);
		
		
		return selectOne == null?false:true;
	}

}
