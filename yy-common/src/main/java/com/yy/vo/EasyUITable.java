package com.yy.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable  {
	/**
	 * 属性:
		1.int total:记录总数
		2.List<?>rows: 表示记录信息
	 * 
	 * */
	private Integer total;
	private List<?> rows;
	

}
