package com.yy.thread;

import com.yy.pojo.User;

public class UserThreadLocal {

	//只有一份.   线程 小抽屉,共享空间. MAP
	/*
	 *    ThreadLocalMap map = getMap(t);
	 * 
	 * */
		private static ThreadLocal<User>  userThread =new ThreadLocal<>();
		
		//数据放入
		public static void set(User user) {
			userThread.set(user);
		}
		
		//数据获取
		public static User get() {
			return userThread.get();
			
		}
		
		//不能存太多,Map 一直存,会越存越多.
		public static void remove() {
			userThread.remove(); //移除当前线程的 map
		}
		
}
