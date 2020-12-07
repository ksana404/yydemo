package com.yy.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("tb_item_desc")
@Data
@Accessors(chain=true) //链式加载
public class ItemDesc extends BasePojo {
	@TableId //标识主键
  private Long itemId; 
  private String itemDesc; 
}
