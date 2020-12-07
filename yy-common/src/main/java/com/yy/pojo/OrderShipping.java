package com.yy.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
/**订单物流表 实体类
 * @author Administrator
 *
 */
@TableName("tb_order_shipping")
@Data
@Accessors(chain=true)
public class OrderShipping extends BasePojo{
	//订单物流表
	
	@TableId
    private String orderId; //订单id

    private String receiverName;//收货人名字

    private String receiverPhone;//收货人固定电话

    private String receiverMobile;//收货人移动电话

    private String receiverState;//收货状态

    private String receiverCity;//收货地址-城市

    private String receiverDistrict;//收货地址-区县

    private String receiverAddress;//收货地址-详细地址-乡镇街道门牌号

    private String receiverZip;////收货邮编
    
}