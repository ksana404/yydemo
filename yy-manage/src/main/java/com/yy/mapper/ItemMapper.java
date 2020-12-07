package com.yy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.pojo.Item;

@Mapper
public interface ItemMapper extends BaseMapper<Item>{
	
	/**
	 * @return
	 */
	List<Item> findAllItems();

	/** 根据当前页面第一行 和页面大小 查询当前页面List.
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	List<Item> findAllPage(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);


	/**根据一组ID 删除商品信息
	 * @param ids
	 * @return
	 */
	Integer deleteByIds(@Param("ids")Long[] ids);

	/** 根据ids和状态码修改状态
	 * @param ids
	 * @param status
	 */
	void updataSatus(@Param("ids")Long[] ids, @Param("status")Integer status);

}
	