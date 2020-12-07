package com.yy.aop.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) //表示注解生效时间-生命周期
@Target(METHOD) //标识注解 作用元素的类型
public @interface CachePoint {
	/**
	 *  
	 *     要求:key不同的业务一定不能重复.
	 *     
	 *     规则:
	 *     key值默认为"",
	 *               如果用户添加了key.则使用用户的
	 *               如果用户没有添加key,
	 *                   生成策略: 包名.类名.方法名::第一个参数
	 *     
	 *     添加时间: 如果不为0则需要数据添加过期时间 setex
	 */
	String key() default "";  //注解特有的属性声明格式.
	int seconds() default 0;
	

}
