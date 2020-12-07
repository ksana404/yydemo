package com.yy.dubbo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yy.mapper.CartMapper;
import com.yy.pojo.Cart;
import com.yy.service.DubboCartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DubboCartServiceImpl implements DubboCartService {
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> queryCartListByUserId(Long userId) {
		Cart cart =new Cart();
		cart.setUserId(userId);
		//声明一个变量,用来指引对象.	//构造方法的简写,传入参数来 新建对象.
		Wrapper<Cart> queryWrapper =new QueryWrapper<>(cart);
		return cartMapper.selectList(queryWrapper);
	}

	/**
 购物车中的商品,是使用 ItemId和UserId来确定唯一标识.
 所以上面 提供了商品ID,和暂时确定,从TrealLocal获取的用户ID.
 我们还需要确定一件事,相同商品的 数量增加,应该只是增加数量,而不再新增购物车商品.

注意:一次新增仅仅涉及一种商品!
 核心代码思考:需要对用户ID和商品ID查询出来的商品进行确认,如果已经有商品,则进行数量增加.如果没有,才新增.
 结果是重定向到购物车商品列表页面,查询结果(更新).
	 */
	@Override
	public void catAdd(Cart cart) {
		//0.需要根据 userId和itemId来查询数据库
		// 条件数据准备
		QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		Cart selectOne = cartMapper.selectOne(queryWrapper);
		//判断
		if(selectOne ==null) {
			//说明 购物车商品列表里还没有添加
			//则下面添加新的Cart数据
			//添加数据,只是添加需要的就行了
			//cartDB
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			//输入插入
			cartMapper.insert(cart);
		}else {
			//说明之前已经存在了 该商品
			//更新数量
			selectOne.setNum(selectOne.getNum()+cart.getNum())
			.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.updateById(selectOne);
			
		}
		
		
	}

	@Override
	public void catDelete(Cart cart) {
		//查看是否有数据,
		//没有直接返回,有的话,删除再返回
		
		//0.需要根据 userId和itemId来查询数据库
				// 条件数据准备
				QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
				queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
				Cart selectOne = cartMapper.selectOne(queryWrapper);
		if(selectOne!=null) {
			cartMapper.delete(queryWrapper);
		}
	}

	/**根据用户 ID ,商品ID,商品数量 num 来更新商品数量.
	 *
	 */
	@Override
	public void catUpdate(Cart cart) {
		//cart 里面的数据有 根据用户 ID ,商品ID,商品数量 num.
		// 条件数据准备
		UpdateWrapper<Cart> updateWrapper =new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		
//		QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
//		queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		//执行更新
		cart.setCreated(new Date()).setUpdated(cart.getCreated());
		int update = cartMapper.update(cart, updateWrapper);
		log.error("------------ >>>>>>> 购物城商品 数量更新:"+update);
		
		
	}

}
