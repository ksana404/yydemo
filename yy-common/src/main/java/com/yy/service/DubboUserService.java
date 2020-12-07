package com.yy.service;

import com.yy.pojo.User;

/**Dubbo通用层接口   用户服务接口
 * @author Administrator
 * 
 * 使用 Dubbo 做前缀,区别那些非 Dubbo的接口.
 *
 */
public interface DubboUserService{

	/**根据用户 姓名 密码  电话 注册用户.
	 * @param username
	 * @param password
	 * @param phone
	 */
	void doRegister(User user);

	/**根据用户名和密码,查询 数据库,用户是否存在.如果存在返回一个 ticket,如果不存在则返回Null
	 * @param user
	 * @return
	 */
	String findUserUP(User userl,String userIP);


}
