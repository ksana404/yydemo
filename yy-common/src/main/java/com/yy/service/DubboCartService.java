package com.yy.service;

import java.util.List;

import com.yy.pojo.Cart;

/**Dubbo远程接口
 * @author Administrator
 *
 */
public interface DubboCartService {

	/**根据该用户的id查询,该用户的所有 购物车商品信息.
	 * @param userId
	 * @return
	 */
	List<Cart> queryCartListByUserId(Long userId);

	/**用户添加商品到购物车
	 * @param cart
	 */
	void catAdd(Cart cart);

	/**用户在购物车 商品列表中 删除商品(删除一个)
	 * @param cart
	 */
	void catDelete(Cart cart);

	/**根据用户 ID ,商品ID,商品数量 num 来更新商品数量.
	 * @param cart
	 */
	void catUpdate(Cart cart);

}
