package com.yy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.ItemCat;
/**Category 商品类目的持久层
 * @author Administrator
 *
 */
@Mapper
public interface ItemCatMapper extends BaseMapper<ItemCat> {

	/**
	 * @param parentId
	 * @return
	 */
	List<ItemCat> findItemCatByParentId(Long parentId);

}
