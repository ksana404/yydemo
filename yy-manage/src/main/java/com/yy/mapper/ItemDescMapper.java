package com.yy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.ItemDesc;


/**
 * @author Administrator
 *
 */
@Mapper
public interface ItemDescMapper extends BaseMapper<ItemDesc>{

	/**根据一组ID 删除商品详情
	 * @param ids
	 * @return
	 */
	Integer deleteByIds(@Param("ids")Long[] ids); 
}
