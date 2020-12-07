package com.yy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.mapper.ItemDescMapper;
import com.yy.mapper.ItemMapper;
import com.yy.pojo.Item;
import com.yy.pojo.ItemDesc;
import com.yy.service.ItemService;
import com.yy.vo.EasyUITable;

import redis.clients.jedis.JedisCluster;

/**业务层实现层
 * 
 * @author Administrator
 *
 */
@Service
public  class ItemServiceImpl implements ItemService{
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Autowired
	private JedisCluster jedis;
//	private Jedis jedis;
	
	

	@Override
	public List<Item> findAllItems() {
		List<Item> findAllItems = itemMapper.findAllItems();
		
		return findAllItems;
	}

	@Override
	public List<Item> findAll() {
		//   条件封装器
	//1. new 条件封装器 条件封装器
	//	QueryWrapper<Item> queryWrapper =new QueryWrapper();
		
		List<Item> selectList = itemMapper.selectList(null);
		
		
		return selectList;
	}

	/**
	 *
	 */
	@Override
	public EasyUITable findAllPage(Integer pageIndex, Integer pageSize) {
		//1.查询总条数
		Integer selectCount = itemMapper.selectCount(null);
		//2.手写分页查询
		/**
		 * 根据
		 * 总条数selectCount,
		 * 当前页码数page,
		 * 页面大小rows,
		 *    查询单页 页面list对象 EasyUITable
		 * 1页 1 2 3 4 5
		 * 2页 6 7 8 9 10
		 * 3页 11 12 13 14 15
		 * 当前页面的起始条数为 startIndex endIndex
		 * startIndex= (pageIndex -1)*pageSize +1
		 * endIndex= (pageIndex -1)*pageSize +pageSize =pageIndex*pageSize
		 * */
		//当前页面 起始条数,第一行
		Integer startIndex=(pageIndex -1)*pageSize +1;
		//当前页面 终止条数,最后一行
		//Integer endIndex=pageIndex*pageSize;
		
		//3.根据当前页面第一行 和页面大小 查询当前页面List.
		//计时
		Long startTime = System.currentTimeMillis();
		
		List<Item> itemList =itemMapper.findAllPage(startIndex-1,pageSize);
		
		Long Totaltime = System.currentTimeMillis()-startTime;
		System.out.println(" 手写分页查询 总耗时为:"+Totaltime);
		
		
		
		
		
		return new EasyUITable(selectCount,itemList);
	}
	@Override
	public EasyUITable findItemPage(Integer pageIndex, Integer pageSize) {
		/*
		 * //1.查询数据总条数 Integer selectCount = itemMapper.selectCount(null); //2.新建page对象
		 * //当前页面 起始条数,第一行 Integer startIndex=(pageIndex -1)*pageSize +1;
		 * 
		 * 
		 * Page<Item> itemPage = new Page<>(startIndex,pageSize);
		 * //参数一是当前页，参数二是每页个数,参数三是数据总条数 QueryWrapper<Item> qw =new QueryWrapper<>();
		 * qw.orderByDesc("updated"); //计时 Long startTime = System.currentTimeMillis();
		 * IPage<Item> iPage = itemMapper.selectPage(itemPage, qw);
		 * 
		 * long total = itemPage.getTotal(); long size = itemPage.getSize(); long pages
		 * = itemPage.getPages(); long current = itemPage.getCurrent();
		 * 
		 * // List<Item> itemList = itemPage.getRecords(); // List<Item> records =
		 * iPage.getRecords();
		 * 
		 * Long Totaltime = System.currentTimeMillis()-startTime;
		 * System.out.println("======== >>> Mybatis分页查询 总耗时为:"+Totaltime);
		 */
		
		//开始毫秒数
				Long startTime = System.currentTimeMillis();
				//封装分页参数
				Page<Item> itemPage = new Page<>(pageIndex, pageSize);
				QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
				queryWrapper.orderByDesc("updated");

				//iPage是分页操作工具API 总记录数/分页结果/条数....
				IPage<Item> iPage = itemMapper.selectPage(itemPage, queryWrapper);
				int count = new Long(iPage.getTotal()).intValue();
				List<Item> itemList = iPage.getRecords();
				Long endTime = System.currentTimeMillis();
				System.out.println("Mybatis-Plus 执行时间:"+(endTime-startTime));
				
		return new EasyUITable(count,itemList);
	}
	/**
	 * 关于主键自增赋值问题
	 * 	说明:一般条件下,当数据入库之后,才会动态的生成主键
	 * 信息.
	 * 	如果在关联操作时,需要用到主键信息时,一般id为null.
	 * 需要动态查询!!!!
	 * 
	 * 思路1: 先入库   在查询 获取主键Id 98%正确
	 * 思路2: 先入库   动态回传主键信息 mysql配合MP实现
	 * 一般解决思路:需要开启mysql数据的主键回传配置.
	 */
	@Transactional
	@Override
	public void save(Item item, ItemDesc itemDesc) {
		//1.保存商品信息
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		int insert = itemMapper.insert(item);
		System.out.println("================= 新增商品ID为:"+item.getId());
		/*
		 * if(insert!=1) { Throws new Exception("商品信息添加失败!"); }
		 */
		
		//2.保存商品详情信息  会返回过来 商品ID ??????
		itemDesc.setItemId(item.getId()).setCreated(new Date()).setUpdated(itemDesc.getCreated());
		int insert2 = itemDescMapper.insert(itemDesc);
		//3.
	
		
		
		
	}

	@Override
	public void update(Item item, ItemDesc itemDesc) {
		//1.修改时间更新
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		//2.
		itemDesc.setItemId(item.getId()).setUpdated(new Date());
		itemDescMapper.updateById(itemDesc);
		
	}

	/**
	 *
	 */
	@Override
	public void delete(Long[] ids) {
		//0.参数校验???? 以及异常抛出???
		System.out.println("==========>>>> 输出IDS"+ids.toString());
		//把String转化成 list<Integer>
		/*
		 * List<String> idStrList = java.util.Arrays.asList(ids.split(","));
		 * List<Integer> idList=new ArrayList<>(); for(String idstr:idStrList) { Integer
		 * idInteger = Integer.parseInt(idstr); idList.add(idInteger); }
		 */
		//1.先根据商品id 删除商品详情信息
		// Integer deleteBatchIds = itemDescMapper.deleteBatchIds(idList);
		//System.out.println("哈哈哈哈========== 商品详情成功删除个数为: "+deleteBatchIds);
		//2.先根据商品id 删除商品信息
		 //Integer deletes = itemMapper.deleteBatchIds(idList);
		 //System.out.println("吼吼吼========== 商品信息成功删除个数为: "+deletes);
		
		
		// 二种,使用xml配置文件中的sql实现,多个删除
		//删除商品详情   应该要先删除 商品详情,再删除商品信息
		//1.参数校验
		
		
		  int itemDescRows = itemDescMapper.deleteByIds(ids);
		  System.out.println("吼吼吼========== 商品详情!! 成功删除个数为: "+itemDescRows);
		 
		 		 
		//删除商品信息
		  int itemRows = itemMapper.deleteByIds(ids);
		  System.out.println("哈哈哈哈========== 商品信息  成功删除个数为: "+itemRows);
		
		
		
	}

	@Override
	public void updateState(Long[] ids, Integer status) {
		//参数校验 
			//修改为下架
			itemMapper.updataSatus(ids,status);
	
	}

	
	@Override
	public ItemDesc queryItemDesc(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public Item queryItem(Long itemId) {
		
		return itemMapper.selectById(itemId);
	}
	
	
	
	
	

}
