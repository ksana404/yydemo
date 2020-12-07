package com.yy.service;

import java.util.List;

import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;
import com.yy.vo.EasyUITable;

/** 商品业务层接口
 * @author Administrator
 *
 */
public interface ItemService {
	
	/**mybatis-plus 查询
	 * @return
	 */
	List<Item> findAll();

	/**普通查询
	 * @return
	 */
	List<Item> findAllItems();

	/** 手写分页 通过 当前页码值,和页面大小,查询分页
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUITable findAllPage(Integer pageIndex, Integer pageSize);

	/**Mybatis-Plus分页  通过 当前页码值,和页面大小,查询分页
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUITable findItemPage(Integer page, Integer rows);

	/**添加商品信息
	 * @param item
	 * @param itemDesc
	 */
	void save(Item item, ItemDesc itemDesc);

	/**根据商品id 修改商品信息
	 * @param item
	 * @param itemDesc
	 */
	void update(Item item, ItemDesc itemDesc);


	/**根据 选择的id(多个)删除商品信息,以及商品详情
	 * @param ids
	 */
	void delete(Long[] ids);

	/**根据 ids 和 state 状态(1正常  2下架) 
	 * @param ids
	 * @param status
	 */
	void updateState(Long[] ids, Integer status);

	/**根据商品id查询商品详情
	 * @param itemId
	 * @return
	 */
	ItemDesc queryItemDesc(Long itemId);

	/**根据商品id查询商品信息
	 * @param itemId
	 * @return
	 */
	Item queryItem(Long itemId);

}
