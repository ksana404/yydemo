package com.yy.service;

import java.util.List;

import com.yy.pojo.User;

/**  用户业务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**查询所有的User信息
	 * @return
	 */
	List<User> queryAll();

	/**通过参数来校验 用户注册信息(2.type   1 username、2 phone、3 email)
	 * @param param
	 * @param type
	 * @return
	 */
	boolean findUserType(String param, Integer type);
	
	
}
