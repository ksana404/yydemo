package com.yy.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**订单基本表  实体类
 * @author Administrator
 *
 */
@TableName("tb_order")
@Data
@Accessors(chain=true)
public class Order extends BasePojo{
	// 订单基本表 
	
	//订单 物流信息
	@TableField(exist=false)	//入库操作忽略该字段
	private OrderShipping orderShipping;
		
	
	//订单 商品信息
	//封装订单商品信息  一对多
	@TableField(exist=false)	//入库操作忽略该字段
	private List<OrderItem> orderItems;  
	
	/**
	 * orderId不是主键自增,使用uuid
	 */
	@TableId
    private String orderId; //订单ID
    private String payment; //实付金额 精确到2位数
    private Integer paymentType; //支付类型 1.在线支付  2.货到付款
    private String postFee;   //邮费 精确到2位数
    private Integer status;	//状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭
    private Date paymentTime; //付款时间
    private Date consignTime; //发货时间
    private Date endTime;		//交易完成时间
    private Date closeTime;		//交易关闭时间
    private String shippingName;//物流名称
    private String shippingCode;//物流单号
    private Long userId;		//用户id
    private String buyerMessage;//买家留言
    private String buyerNick;	//卖家昵称
    private Integer buyerRate;	//买家是否已经评价

}