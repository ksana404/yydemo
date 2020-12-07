package com.yy.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ExceptionAOP {
	
	//1.确定切入点
	//2.构建环绕方法
	/**
 说明:要求获取项目中全部异常信息
 需求:要求拦截项目中全部的异常信息.
 
1.并且获取异常的类型.
2.获取异常信息.
3.获取目标对象的类型.


	 * 	环绕通知的语法
	 * 	返回值类型:  任意类型用Obj包裹 
	 * 	参数说明:      必须包含并且位置是第一个  
	 * 			   ProceedingJoinPoint
	 * 	通知标识:	 
	 * 		1.@Around("切入点表达式")
	 * 		2.@Around(切入点())
	 */
	@AfterThrowing(pointcut ="execution(* com.yy..*.*(..))",throwing = "throwable")
	public void afterThrowMethod(JoinPoint jointPoint,Throwable throwable) {//与上面保持一致啊,哥哥
		//Throwable
	
		
		//5.获取目标对象 类型
		Class<?> className = jointPoint.getTarget().getClass();
		//6.获取方法的名称
		 String methodName = jointPoint.getSignature().getName();
		//7.获取异常类型
		 Class<?> throwableClassName = throwable.getClass();
		 //8.获取异常信息
		 String throwableMessage = throwable.getMessage();
		 
		System.out.println("--------->>>>目标对象的类型:"+className+"目标方法的名称:"+methodName);
		System.out.println("--------->>>>异常的类型:"+throwableClassName+"异常的信息:"+throwableMessage);
		log.error("======== log =============== log ======="+"异常的类型:"+throwableClassName+"异常的信息:"+throwableMessage);
		
	}

}
