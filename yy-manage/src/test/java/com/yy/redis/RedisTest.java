package com.yy.redis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@SpringBootTest
public class RedisTest {
	
	 private Jedis jedis; //声明一个引用变量类型 Jedis类型,用于指引对象.
	 
	 @Before //在执行Test 方法之前执行
	 public void innit() {
		 jedis =new Jedis("192.168.64.184",6379);  //new一个Jedis对象,根据参数指定连接.
		 
		 
	 }
	 
	 
	 //String类型 测试
	 @Test
	 public void testString() throws InterruptedException {
		 //1.set
		 String k1 ="2020";
		 String v1 ="每天越来越好!";
		 String v2 ="每";
		 String v3 ="A";
		 
		 jedis.set(k1, v1);
		 System.out.println("v1:"+jedis.get(k1));
		 System.out.println("v1.length:"+jedis.strlen(k1));
		 jedis.set(k1, v2);
		 System.out.println("v2:"+jedis.get(k1));
		 System.out.println("v2.length:"+jedis.strlen(k1));
		 jedis.set(k1, v3);
		 System.out.println("v3:"+jedis.get(k1));
		 System.out.println("v3.length:"+jedis.strlen(k1));
		 
		 /**英文符号和英文的长度为1.中文为3.
		  *  jedis.set(k1, v1); 是会覆盖原来的value,如果原来有value的话.
		  * 
		  * */
		 System.out.println("1: -------------->");
		 
		 jedis.set(k1, v1);
		 //根据key 获取对应value;
		 System.out.println("v1:"+jedis.get(k1));
		 //根据key 获取对应value 的长度;
		 System.out.println("v1.length:"+jedis.strlen(k1));
		 System.out.println("k1.exists:"+jedis.exists(k1) );
		 
		 String k2 ="2030";
		        v2 ="看不到我";
		 System.out.println("0 k2.exists:"+jedis.exists(k2) );
		 jedis.set(k2, v2);
		 System.out.println("1 k2.exists:"+jedis.exists(k2) );
		
		 // jedis.setnx(k.v);
		//2.如果key已经存在,则不允许操作redis.
		//原理:只能操作不存在的key  0 失败  /1 成功!!!
		 
		 System.out.println("setnx 练习 -------->");
		 String k3 ="2040";
		 Long setnx = jedis.setnx(k3,v3);
		 
		 System.out.println("0 v3赋值情况:"+setnx);
		 System.out.println("v3:"+jedis.get(k1));
		 
		 Long setnx1 = jedis.setnx(k3,v3);
		 System.out.println("1 v3赋值情况:"+setnx1);
		 System.out.println("v3:"+jedis.get(k1));
		 
		 Long setnx2 = jedis.setnx(k3,v3+"77");
		 System.out.println("2 v3赋值情况:"+setnx2);
		 System.out.println("v3:"+jedis.get(k1));
		 
		 
		//3.为数据添加超时时间 10秒
		 String k4 ="2050";
		 String v4 ="世界一百年";
		 jedis.set(k4, v4);
		 jedis.expire(k4, 10);
		 System.out.println("0   k4的v4存活时间:"+jedis.ttl(k4));
		 
		 Thread.sleep(5000L);
		 System.out.println("1   k4的v4存活时间:"+jedis.ttl(k4));
	
//		
//		  jedis.setex("1908", 20, "随机"); //jedis.psetex(key, milliseconds, value)
//		  Thread.sleep(3000L); System.out.println("存活时间:"+jedis.ttl("1908"));
//		 
		 
		 /**总结: 
			 * ==1. 普通 放入和取出: jedis.set();  把key、value set到redis中，隐含覆盖，默认的ttl是-1（永不过期）
			 * String set(final String key, final String value)
			 *  --String 状态码   成功为 ok  失败为 ?
			 *  String get(final String key)
			 *  --String 获取 key 对应value 返回(key 不存在或者为 空串 都返回 null)
			 *  
			 *  ==2.只能放入 key 不存在的           setnx
			 *   Long setnx(final String key, final String value)
			 * --long 标识符 成功为1   失败为0
			 * 
			 * 
			 * ==3.  存活 有效时间
			 * ---- 设置存活时间
			 * --3.1          set(k,v) +  expire(k,int seconds)
			 *  String set(final String key, final String value)
			 *  Long expire(final String key, final int seconds)
			 *  
			 * --3.2  setex
			 * String setex(final String key, final int seconds, final String value)
			 * 
			 * ---- 获取存活时间
			 * Long ttl(final String key)  
			 * --Long 为秒
			 * 
			 * 
			 * */
	 }
	 
		/**
		 * 要求:setnx和setex的方法要求同时完成
		 * 实际用法: 实现redis分布式锁的关键.
		 */
	 @Test
	public void testNXEX() {
			
			//String result = jedis.set("abc", "小学生之手,人头狗", "NX", "EX", 20L);
			String result = jedis.set("abc", "小学生之手,人头狗");
			System.out.println("成功返回OK,不成功返回null");
			System.out.println("获取结果:"+result);
		}
	 
	 @Test
		public void testNXEX01() {
	/** String set(final String key, final String value, final String nxxx, final String expx, final int time) 
			-- final String key			放入Redis 的key
			-- final String value		放入Redis 的value
			-- final String nxxx    可选"nx"或者"xx" ,"nx" 表示不存在才放入(新建), "xx"只有已经存在key 存在才放入(覆盖)
			-- final String expx	可选"ex"或者"px" , 表示下面参数time单位 ,  ex ： seconds 秒,                px :   milliseconds 毫秒;
			-- final int time		过期时间
	 */
			String k100="100";
			Long del = jedis.del(k100);
			Long del01 = jedis.del(k100);
			System.out.println("删除k100情况,初始化:"+del);
			System.out.println("多次删除k100情况,初始化:"+del01);
			//System.out.println("kkk 放入情况:"+jedis.set("kkk", "vvv")); 
			
			// 新建  测试 --yes  no
			 System.out.println("不存在 超时 时间还剩 为:"+jedis.ttl(k100));
			 String set = jedis.set(k100, "你们猜猜看");
			 System.out.println("存在 超时 时间还剩 为:"+jedis.ttl(k100));
			 
			 System.out.println("第一次放入情况 set k100:"+set);
			 
		     Long setnx = jedis.setnx(k100, "听说 nx 是必须不存在的才能放入?");
		     System.out.println("第二次放入情况  setnx:"+setnx);
		     
		     System.out.println("中间key100的value:"+jedis.get(k100));
		     System.out.println("=============");
		  //   SetParams setParams =new SetParams();
		     
		   //这是一个参数类型,使用它来确定  确保key是否已经存在 可选"nx"或者"xx" 和过期时间单位 可选"ex"或者"px" 
		     // SetParams对象 需要注意参数的搭配,
		     //1.第一种)不添加任何参数和set默认一样,key覆盖,key长期有效.
		     //2.添加参数可以,第二种)可以只添加key是否已经存在  nx"|"xx" ,第三种)或者是 过期时间单位 可选"ex"|"px",第四种 是key是否存在选一个,过期时间选一个.
		     //注意 千万不能 两种过期时间单位 可选"ex"|"px" 选两个,和过期时间 选两个.
		     
		     //不设置过期时间 ex().px(),则默认永不过期,返回为-1
		     
		     
		     // del = jedis.del(k100);
			System.out.println("再次删除k100情况,初始化:"+del);
		   //  setParams.xx();// 这个key 已经存在
		  // setParams.nx();//  这个key 不能存在
		   // setParams.ex(10); //  这个ke过期时间单位为 秒,数值为10.
		    // setParams.px(5000L); //这个ke过期时间单位为 毫秒,数值为50000.
		     /**
		      * 
		      * 
		      * */
		     
		     //所以这里全都由 参数类型来确定.
		  //   String 结果 = jedis.set(k100, "原来的key不能存在  已经删除   我来试试看", setParams);
			
			
			//System.out.println(" !!!放入   成功 就会返回OK,不成功返回null");
			//System.out.println("获取结果   结果结果结果 已经存在,可以放入.  结果是     :"+结果);
			
			System.out.println("k100 value 为:"+jedis.get(k100));
			System.out.println("k100 超时 时间还剩 为:"+jedis.ttl(k100));
		}
		/** 1. nx 测试,修改时间,可以看到时间过期后,可以添加.
		 * ----------- key 超时后 set成功.
		 * 
		 * 2. xx 测试,不在之前set同样的key,设置失败,超时时间 -2;
		 * 		在之前 set,设置成功, key 超时后 set失败.
		 * ----------- key 超时后 set失败
		 * 
		 * 
		 * 
		 * */
	 
	 @Test
	 public void hashTest() {
		 /**
		  * jedis.hset("key","field","value"); 
		  * String value= jedis.hget("key","field");
		  * Map<String, String> hash= jedis.hgetAll("key");
		  * 
		  *  public Long hset(final String key, final String field, final String value) {
    checkIsInMultiOrPipeline();
    client.hset(key, field, value);
    return client.getIntegerReply();
  }
  
  
   public Long hset(final String key, final Map<String, String> hash) {
    checkIsInMultiOrPipeline();
    client.hset(key, hash);
    return client.getIntegerReply();
  }
  
  
  @Override
  public String hget(final String key, final String field) {
    checkIsInMultiOrPipeline();
    client.hget(key, field);
    return client.getBulkReply();
  }
  
  
  @Override
  public String hget(final String key, final String field) {
    checkIsInMultiOrPipeline();
    client.hget(key, field);
    return client.getBulkReply();
  }
  
  
   @Override
  public Long hsetnx(final String key, final String field, final String value) {
    checkIsInMultiOrPipeline();
    client.hsetnx(key, field, value);
    return client.getIntegerReply();
  }
		  * 
		  * */
		 
		 /**
		  * 
		   jedis.hset("key","field","value"); 
		  * String value= jedis.hget("key","field");
		  * Map<String, String> hash= jedis.hgetAll("key");
		  * */
		 
		 jedis.hset("person","name","kong"); 
		 jedis.hset("person","age","22"); 
		 jedis.hset("person","sex","female"); 
		 
		 Map<String, String> hgetAll = jedis.hgetAll("person");
		 Set<Entry<String, String>> entrySet = hgetAll.entrySet();
		 for(Entry<String, String> entry:entrySet) {
			 System.out.println(entry.toString());
			 
		 }
		 
	 }
	 
	 @Test
	 public void listTest() {
		 /**
说明:Redis中的List集合是双端循环链表,分别可以从左右两个方向插入数据.
List集合可以当做队列使用,也可以当做栈使用
队列:存入数据的方向和获取数据的方向相反
栈:存入数据的方向和获取数据的方向相同
		  * lpush  从左边入栈从队列的左边入队一个或多个元素	LPUSH key value [value ...]
		  * rpop  从右边出栈  从队列的左端出队一个元素	LPOP key
		  * lpushx	当队列存在时从队列的左侧入队一个元素	LPUSHX key value
		  * lrange	从列表中获取指定返回的元素	  LRANGE key start stop    Lrange key 0 -1 获取全部队列的数据
		  * 
		  * */
		 jedis.lpush("listDemo", "a", "b", "c", "d", "e");
		 //lpush: edcba  ->左边入队  注意我们的书写顺序 和机器读代码的顺序都是从  左到右
		 //lrange:从左边开始查询元素   0-2: edc
		 
		 List<String> lrange = jedis.lrange("listDemo", 0, 2);
		 System.out.println("0 lrange:"+lrange.toString());
		 
		 jedis.rpush("listDemoA", "A", "B", "C", "D", "E");
		 //rpush: ABCDE  ->右边入队   注意我们的书写顺序 和机器读代码的顺序都是从  左到右
		 //lrange:从左边开始查询元素   0-2: ABC
		 List<String> lrange2 = jedis.lrange("listDemoA", 0, 2);
		 System.out.println("2 lrange2:"+lrange2.toString());
	
		 
	 }
	 
	 
	 @Test
	 public void transactionTest() {
		 //事务控制
		 /*开启事务
		  * 	Transaction transaction = jedis.multi();
		  * 事务回滚
		  *     transaction.discard();
		  * 
		  * 
		  * */
		 Transaction multi = jedis.multi();
		 try {
			 System.out.println(multi.get("tranKey"));
			 System.out.println(multi.get("tranKey01"));
			// multi.set("tranKey", "tranValue");
			// multi.setnx("tranKey", "tranValue"); //失败结束
			 multi.set("tranKey03", "tranValue03");
			 System.out.println(multi.get("tranKey03"));
			 multi.exec();
		} catch (Exception e) {
			multi.discard();
		}
		 
		 
		 
		 
	 }
		
	

}
