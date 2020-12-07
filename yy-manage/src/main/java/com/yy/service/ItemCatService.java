package com.yy.service;

import java.util.List;

import com.yy.pojo.ItemCat;
import com.yy.vo.EasyUITree;

/**商品类目的接口层
 * @author Administrator
 *
 */
public interface ItemCatService {

	/**根据商品id查询商品 Category 类目
	 * @param itemCatId
	 * @return
	 */
	ItemCat queryItemCatById(Integer itemCatId);

	/**根据商品id查询商品 Category 类目 叶子信息
	 * @param parentId
	 * @return
	 */
	List<EasyUITree> findItemCatByParentId(Long parentId);

	/** 从parentId 中 Rdis缓存中查询商品类目  叶子信息
	 * @param parentId
	 * @return
	 */
	List<EasyUITree> findItemCatCach(Long parentId);

	/**查询所有的商品类目
	 * @return
	 */
	List<ItemCat> queryItemCatAll();



}
