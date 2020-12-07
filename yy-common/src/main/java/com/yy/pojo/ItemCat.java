package com.yy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_cat") //映射表的名字
@Data
@Accessors(chain = true)  //可以使用装饰模式,一路点出来
public class ItemCat extends BasePojo {

	@TableId(type = IdType.AUTO)
	private Long id;		//商品分类id号
	private Long parentId;	//商品分类父级ID  父ID=0时，代表一级类目
	private String name;	//商品分类名称
	private Integer status;	//商品分类状态  1正常，2删除
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//是否为父级  使用该属性判断是否为一二级分类
}
