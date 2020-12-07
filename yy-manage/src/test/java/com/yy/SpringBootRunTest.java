package com.yy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@MapperScan("com.yy.mapper") //扫描实体层
@SpringBootTest
public class SpringBootRunTest {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRunTest.class, args);
	}
}
