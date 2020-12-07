package com.yy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;
import com.yy.service.ItemService;
import com.yy.vo.EasyUITable;
import com.yy.vo.SysResult;


@RestController
@RequestMapping("item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("findAll")
	public List<Item> findAll() {
    //		return itemService.findAllItems();
		return itemService.findAll();
		
	}
	
	/**
	 * itemList
	 * 
	 * http://localhost:9091/item/query?page=1&rows=20 404
	 *  返回值 EasyUITable
	 * */
	
	@RequestMapping("query")
	public EasyUITable query(Integer page,Integer rows) {
    //		return itemService.findAllItems();
		//手写分页
		EasyUITable table = itemService.findAllPage(page,rows);
		//Mybatis分页
		EasyUITable table1 = itemService.findItemPage(page,rows);
		
		return  table;
		
	}
	

	/**
	 * 
	 	 * 关于主键自增赋值问题
	 * 	说明:一般条件下,当数据入库之后,才会动态的生成主键
	 * 信息.
	 * 	如果在关联操作时,需要用到主键信息时,一般id为null.
	 * 需要动态查询!!!!
	 * 
	 * 思路1: 先入库   在查询 获取主键Id 98%正确
	 * 思路2: 先入库   动态回传主键信息 mysql配合MP实现
	 * 一般解决思路:需要开启mysql数据的主键回传配置.
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@RequestMapping("save")
	public SysResult save(Item item,ItemDesc itemDesc) {
		try {
			//0.数据添加
			itemService.save(item,itemDesc);
			
			return  SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	
		
	}
	
	/**
	 *  http://localhost:9091/item/update
	 * 
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	@RequestMapping("update")
	public SysResult update(Item item,ItemDesc itemDesc) {
		try {
			//0.数据添加
			itemService.update(item,itemDesc);
			
			return  SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	
		
	}
	
	
	@RequestMapping("reshelf")
	public SysResult reshelf(Long[] ids) {
		try {
			//上架,把状态修改成1;
			Integer status =1;
			itemService.updateState(ids,status);
			
			return  SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	
		
	}
	
	@RequestMapping("instock")
	public SysResult instock(Long[] ids) {
		try {
			//下架,把状态修改成2;
			Integer status =2;
			itemService.updateState(ids,status);
			
			return  SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	
		
	}
	
	
	
	
	
	/**根据 选择的id(多个)删除商品信息,以及商品详情
	 * @param ids
	 * @return
	 */
	@Transactional
	@RequestMapping("delete")
	public SysResult delete(Long[] ids) {
		try {
			//0.数据删除
			itemService.delete(ids);
			
			return  SysResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	}
	
	
	
	
	@RequestMapping("query/item/desc/{itemId}")
	public SysResult queryItemDesc(@PathVariable Long itemId) {
		try {
			//0.根据商品id查询商品详情
		ItemDesc itemDesc=itemService.queryItemDesc(itemId);
			
			return  SysResult.success(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		
	}
	
	
	
	
	
	
	

}
