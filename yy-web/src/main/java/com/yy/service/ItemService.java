package com.yy.service;

import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;

public interface ItemService {

	/**根据Id查询商品信息
	 * @param itemId
	 * @return
	 */
	Item queryItemById(Long itemId);

	/**根据Id查询商品详情信息
	 * @param itemId
	 * @return
	 */
	ItemDesc queryItemDescById(Long itemId);

}
