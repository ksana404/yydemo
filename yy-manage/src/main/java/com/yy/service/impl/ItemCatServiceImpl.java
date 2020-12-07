package com.yy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.aop.anno.CachePoint;
import com.yy.mapper.ItemCatMapper;
import com.yy.pojo.ItemCat;
import com.yy.service.ItemCatService;
import com.yy.util.ObjectMapperUtil;
import com.yy.vo.EasyUITree;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	// com.yy.service.impl.ItemCatServiceImpl.
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private JedisCluster jedis;
//	private Jedis jedis;
	
	
	

	@Override
	public ItemCat queryItemCatById(Integer itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat;
	}

	/**返回值结果 是一个vo对象
	 * 数据来源于: te_item_cat List<ItemCat>
	 * 如何转化: List<ItemCat> ~~~~ List<EasyUITree>
	 *
	 */
	
	@CachePoint
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		
		//0.定义返回值数据
		List<EasyUITree> treeList =new ArrayList<EasyUITree>();
		
		
		/**
		 * 数据库数据itemCat转化为VO对象
		 * 1.查询 1级
		  SELECT * FROM tb_item_cat WHERE parent_id=0;
		  查询2级
 		  SELECT * FROM tb_item_cat WHERE parent_id=74;
 		  查询3级
          SELECT * FROM tb_item_cat WHERE parent_id=142;
		 * 
		 * 
		 */
		//使用 for 循环
		//1.获取数据库 数据
	
		
		List<ItemCat> itemCatList = queryItemCatByParentId(parentId);
		
		//2.转化  1级菜单关闭  2级菜单关闭   3级菜单打开 
		for(ItemCat itemCat:itemCatList) {
			EasyUITree tree =new EasyUITree();
			//state的状态  如果是父级菜单 应该关闭,不然打开,也就是实现上面  只有三级菜单打开的 默认状态.
			String state= itemCat.getIsParent()?"closed":"open";
			tree.setState(state).setId(itemCat.getId()).setText(itemCat.getName());
			//如果想一路点出出来 需要在 pojo/vo 等添加
			//@Accessors(chain = true)  //可以使用装饰模式,一路点出来
			treeList.add(tree);
		}
		
		
		
		return treeList;
	}

	private List<ItemCat> queryItemCatByParentId(Long parentId) {
		QueryWrapper<ItemCat> qw =new QueryWrapper<ItemCat>();
		qw.eq("parent_id", parentId);
		List<ItemCat> selectList = itemCatMapper.selectList(qw);
		return selectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findItemCatCach(Long parentId) {
		//开始计时
		Long startTime =System.currentTimeMillis();
		
		//findItemCatByParentId 这是原始方法
		//0.定义返回值数据
		List<EasyUITree> treeList =new ArrayList<EasyUITree>();
		
		//1.要从Redis中查询数据,或者放入数据.首先需要确定key,而且不同的(参数不一样) 查询key应该不一样.
		//包名.类名.方法名+参数(可变)
		String key="com.yy.service.impl.ItemCatServiceImpl.findItemCatCach::"+parentId;
		
		//2.根据key从Redis中查询数据
		String valueJson = jedis.get(key);
		//3.对查询到的value进行非空校验
		if(StringUtils.isEmpty(valueJson)) {
			//3.1 如果没有值
			//3.2则从数据库查询数据 selectList
			treeList = findItemCatByParentId(parentId);
			//计算数据库查询时间
			Long sqlTime =System.currentTimeMillis()-startTime;
			System.out.println("======= 数据库查询时间:"+sqlTime);
			
			//3.3 把 treeList 转换成json格式,以便存放到Redis中
			String json = ObjectMapperUtil.toJson(treeList);
			//3.4放入
			jedis.set(key, json);
		}else {
			//4.1如果有值 那么这个值是 json格式
			//4.2 把json格式的value转换成 对象 List<EasyUITree>
			
			 treeList = ObjectMapperUtil.toObject(valueJson, treeList.getClass());
				//计算数据库查询时间
				Long redisTime =System.currentTimeMillis()-startTime;
				System.out.println("======= Redis查询时间:"+redisTime);
				
		}
	
		return treeList;
	}

	@Override
	public List<ItemCat> queryItemCatAll() {
		return itemCatMapper.selectList(null);
	}

}
