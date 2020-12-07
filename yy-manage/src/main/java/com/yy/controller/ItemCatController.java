package com.yy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yy.pojo.ItemCat;
import com.yy.service.ItemCatService;
import com.yy.vo.EasyUITree;

/**商品类目 控制层 商品 Category 类目
 * @author Administrator
 *
 */

@RestController 
@RequestMapping("/item/cat/")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	//1.商品类目 格式化
	/****
	 * 总的方法就是 根据商品类目的id,查询商品类目的名字,返回的也就是这个名字.
	  type:"post",
      url:"/item/cat/queryItemName",
      return name;
      itemCatId: 560
	 * */
	
	@RequestMapping("queryItemName")
	public String queryItemName(Integer itemCatId) {
		//1.现获取 整个商品目录的信息,以后还有用.
		ItemCat itemCat=itemCatService.queryItemCatById(itemCatId);
		return itemCat.getName();
		
	}
	
	
	
	//2.商品类型查询
	/**
	 * http://localhost:9091/item/cat/list 
	 * /**
	 * 实现商品分类查询
	 * 如果是第一次请求则parentId=0
	 * 如果是展开节点,将该节点的Id当做参数发送?id=580
	 * easyUI中异步树加载控件
	 * 
	 * 树控件读取URL。子节点的加载依赖于父节点的状态。
	 * 当展开一个封闭的节点，如果节点没有加载子节点，
	 * 它将会把节点id的值作为http请求参数并命名为'id'，
	 * 通过URL发送到服务器上面检索子节点。
	 * 
	 * 前面之所以 完成一个通过ID来查询类目的名字,就是因为这里需要用到
	 *  /item/cat/queryItemName
 
 3.商品list
 Request URL: http://localhost:9091/item/cat/list
Request Method: POST
Status Code: 404 
	 */
	//通过父节点来查询下面次一级所有子节点,List<EasyUITree>
	@RequestMapping("list")
	public List<EasyUITree> findItemCatByParentId(@RequestParam(name="id",defaultValue="0") Long parentId){
		
		List<EasyUITree>  tree =itemCatService.findItemCatByParentId(parentId);
		//从Redis缓存中查询
	//	List<EasyUITree>  tree =itemCatService.findItemCatCach(parentId);
		
		return tree;
		
	}

	
	

}
