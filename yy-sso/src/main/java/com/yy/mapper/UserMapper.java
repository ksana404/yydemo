package com.yy.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.User;

/**
 * @author Administrator
 * 用户持久层接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{
	
	

}
	