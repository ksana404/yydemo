package com.yy.config;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yy.mapper.OrderMapper;
import com.yy.pojo.Order;

//准备订单定时任务
@Component	//orderQuartz
public class OrderQuartz  extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
 
	
	/**
	 * 将超时30分钟的订单状态码由1改为6
	 * 条件:  //now - created >  30分钟 
	 * 		 created < now-30分钟
	 * 		 and status = 1
	 */
	/*
	 * @Override
	 * 
	 * @Transactional //protected protected void executeInternal(JobExecutionContext
	 * context) throws JobExecutionException { //获取当前时间的日历对象 Calendar calendar =
	 * Calendar.getInstance(); //毫秒/秒/分钟/小时/天/月/年/星期 calendar.add(Calendar.MINUTE,
	 * -30); //获取超时时间 Date timeOut = calendar.getTime(); Order order = new Order();
	 * order.setStatus(6).setUpdated(new Date()); UpdateWrapper<Order> updateWrapper
	 * = new UpdateWrapper<>(); updateWrapper.lt("created",timeOut); //创建时间小于
	 * 30分钟,说明在30分钟之前创建算作超时. 离现在超过了半小时. updateWrapper.eq("status", 1);
	 * orderMapper.update(order, updateWrapper); System.out.println("定时任务执行完成!!!!");
	 * 
	 * }
	 */
}
