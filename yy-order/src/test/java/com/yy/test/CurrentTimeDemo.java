package com.yy.test;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrentTimeDemo {
	

	@Test
	public void testCurrentTimeDemo() {
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("currentTimeMillis:"+currentTimeMillis);
		//1607093008573
		
		Date date =new Date();
		System.out.println("data:"+date);
		
		String numTimeStr = currentTimeMillis+"";
		String substring = numTimeStr.substring(numTimeStr.length()-4);
	
	
		System.out.println("substring:"+substring);
		
		//Thread
		ThreadLocalRandom current = ThreadLocalRandom.current();
		//package java.util.concurrent;
	 
		
		
	}
}
