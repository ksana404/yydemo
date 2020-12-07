package com.yy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisConfigTest {
	
	@Autowired
	private Jedis getJedis;
	
	@Test
	public void getJedisTest() {
		String set = getJedis.set("9977", "测试");
		System.out.println(set+"+结果:"+getJedis.get("9977"));
	}

}
