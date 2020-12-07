package com.yy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)  //可以使用装饰模式,一路点出来
public class EasyUITree {
	private Long id;
	private String text;	//节点名称
	private String state;	//节点闭合/开启 open/closed
	
	/**
	 * 
	 * 数据结构:   “[{id:1,text:”节点名称”,state:”open/closed”},{}]”
	 * */
}
