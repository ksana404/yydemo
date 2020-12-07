package com.yy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@Component
//@Aspect
public class RunTimeAOP {
	
	//1.确定切入点
	//2.构建环绕方法
	/**
 说明:要求获取com.yy.Service中的全部方法的运行时间.
并且输出:
1.目标对象的类型.
2.目标方法的名称.
3.方法的执行时间.

	 * 	环绕通知的语法
	 * 	返回值类型:  任意类型用Obj包裹 
	 * 	参数说明:      必须包含并且位置是第一个  
	 * 			   ProceedingJoinPoint
	 * 	通知标识:	 
	 * 		1.@Around("切入点表达式")
	 * 		2.@Around(切入点())
	 */
	@Around("execution(* com.yy.service..*.*(..))")
	public Object around(ProceedingJoinPoint jointPoint) {
		//开始计时
		Long start =System.currentTimeMillis();
		//3.声明返回值
		//4.执行方法
		Object proceed;
		try {
			proceed = jointPoint.proceed();
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		//5.获取目标对象 类型
		Class<?> className = jointPoint.getTarget().getClass();
		//6.获取方法的名称
		 String methodName = jointPoint.getSignature().getName();
		//7.方法的执行时间
		 Long countTime =System.currentTimeMillis()-start;
		System.out.println("目标对象的类型:"+className);
		System.out.println("目标方法的名称:"+methodName);
		System.out.println("方法的执行时间:"+countTime+"毫秒");
		
		return proceed;
		
	}

}
