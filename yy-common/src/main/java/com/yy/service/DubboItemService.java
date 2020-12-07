package com.yy.service;

import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;

public interface DubboItemService {

	/** 根据商品ID查询商品基本信息
	 * @param itemId
	 * @return
	 */
	Item queryItemById(Long itemId);

	/**根据商品ID查询商品详情信息
	 * @param itemId
	 * @return
	 */
	ItemDesc queryItemDescById(Long itemId);

}
