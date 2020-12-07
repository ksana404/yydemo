package com.yy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.yy.aop.anno.CachePoint;
import com.yy.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

//切面注解
@Component
@Aspect	   //自定义切面
public class RedisCacheAOP {
	//1.注入Jedis
	
//	private Jedis jedis;
	
	@Autowired
	private JedisCluster jedis;
	
	//2.构建环绕通知
	/**
	 * 	环绕通知的语法
	 * 	返回值类型:  任意类型用Obj包裹 
	 * 	参数说明:      必须包含并且位置是第一个  
	 * 			   ProceedingJoinPoint
	 * 	通知标识:	 
	 * 		1.@Around("切入点表达式")
	 * 		2.@Around(切入点())
	 */
	@SuppressWarnings("unchecked")
	@Around("@annotation(cachePoint)")
	public Object around(ProceedingJoinPoint joinPoint,CachePoint cachePoint) {
		//1.定义返回数据类型
		Object result =null;
		//2.获取key值
		//2.1 通过ProceedingJoinPoint 获取方法签名 从而获取 包名.类名.方法名 以及参数,拼接而成.
		//参数是 ProceedingJoinPoint 和 CachePoint (注解类型)
		String key =getKey(joinPoint,cachePoint);
		
		//3. 在Redis中通过key获取value
		String valueJson = jedis.get(key);
		
		Long startTime =System.currentTimeMillis();
		System.out.println("===== ===== 开始RedisAOP查询");
		//4.对value进行非空校验
		if(StringUtils.isEmpty(valueJson)) {
			//5.如果是 空
			//5.1查询数据库 获取返回结果 result
			try {
				
				
				//数据库查出来的一定是对的,所以直接 放入redsult返回没有问题.
			Object	proceed = joinPoint.proceed();
				
				Long sqlTime =System.currentTimeMillis()-startTime;
				System.out.println("===== =====RedisAOP,数据库查询时间"+sqlTime);
				
				//非空校验 ?
				//5.2 result 转换为resultJson
				if(proceed!=null) {
					String resultJson = ObjectMapperUtil.toJson(proceed);
					//key的超时时间  ex超时单位为秒  px为毫秒   xx为key必须已经存在   nx意思是key必须不存在
					if(cachePoint.seconds()>0) {
						jedis.setex(key, cachePoint.seconds(), resultJson);
					}
					//5.3 resultJson存入 Redis
					jedis.set(key, resultJson);
				}
			
				return proceed;
				
				
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			
			
			
		}else {
			
			Long redisTime =System.currentTimeMillis()-startTime;
			System.out.println("===== =====RedisAOP,Redis缓存 查询时间"+redisTime);
			
			//6.如果不为空
			//6.1把 value 转化为 返回值对象
			//6.1.2 获取返回值对象 类型
			Class targetClass =getReturnType(joinPoint);
			
		    //6.1.3 数据转换  并放入其中
			result = ObjectMapperUtil.toObject(valueJson, targetClass);
			
		}
		
		
		
		
		//7.返回数据
		return result;
		
		
	}

	/** 根据 切入点获取方法返回值对象
	 * @param joinPoint
	 * @return
	 */
	private Class getReturnType(ProceedingJoinPoint joinPoint) {
		//1.通过且 连接点对象 获取,方法签名,
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		//2.通过签名获取返回值 类型
		Class returnType = signature.getReturnType();
		
		return returnType;
	}

	/**从而获取 包名.类名.方法名 以及参数,拼接而成.
	 * @param joinPoint  ProceedingJoinPoint
	 * @param cachePoint CachePoint (注解类型)
	 * @return  String key
	 */
	private String getKey(ProceedingJoinPoint joinPoint, CachePoint cachePoint) {
		//通过ProceedingJoinPoint  CachePoint  获取方法签名 从而获取 包名.类名.方法名 以及参数,拼接而成.
		
		//1.查看 注解获取到的key
		String key = cachePoint.key();
		//2.如果 key不为空串
		if(!StringUtils.isEmpty(key)) {
			//2.1 直接使用用户的key 返回
			return key;
			
		}else {
		//3.如果 key为空串,则需要 自动获取key
		// 包名.类名.方法名::参数
		//获取包名.类名
			String className = joinPoint.getSignature().getDeclaringTypeName();
		//获取方法名
			String methodName = joinPoint.getSignature().getName();
		//获取参数
			Object fistArg = joinPoint.getArgs()[0];
			
			return className+"."+methodName+"::"+fistArg;
			
		}
		
	}
	

}
