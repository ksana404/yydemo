package com.yy.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.yy.service.HttpClientService;

import lombok.extern.slf4j.Slf4j;

/**
 * 调用步骤:
 * 	1.确定url的访问地址.
 *  2.确定请求的方式类型 get/post
 *  3.实例化httpClient对象.
 *  4.发起请求. 获取响应response.
 *  5.判断程序调用是否正确  200 302 400参数异常 406 参数转化异常
 *  	404 500 502 504 访问超时...
 *  6.获取返回值数据一般都是String.   JSON
 * @throws IOException 
 * @throws ClientProtocolException 
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)  //需要从容器中获取对象使用
public class TestHttpClient {

	//1.get 远程调用测试
	@Test
	public void getTest() throws ClientProtocolException, IOException {
		String url ="https://www.jianshu.com/";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = HttpClients.createDefault();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		
		//获取状态码信息
		if(200 == httpResponse.getStatusLine().getStatusCode()) {
			String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			log.error(result);
		}
		
	}
	
	
	@Autowired
	private CloseableHttpClient htClient;//从池中获取连接
	@Autowired
	private RequestConfig requestConfig; //控制请求超时时间
	
	@Test
	public void test02() throws ParseException,IOException {
		String url = "http://manage.yy.com/web/item/findItemDescById?itemId=635906";
		HttpGet get = new HttpGet(url);
		get.setConfig(requestConfig);
		
		HttpResponse response = htClient.execute(get);
		//获取状态码信息
		if(200 == response.getStatusLine().getStatusCode()) {
			
			String result = 
				EntityUtils.toString
						(response.getEntity(),"utf-8");
			System.out.println(result);
		}
	}
	
	@Autowired
	private HttpClientService httpClint;
	
	@Test
	public void test03(){
		String url = "http://manage.yy.com/web/item/findItemDescById";
		Map<String,String> params = new HashMap<String, String>();
		params.put("itemId", "562379");
		String result = httpClint.doGet(url, params, null);
		
		String url01 = "http://manage.yy.com/web/item/findItemDescById?itemId=562379";
		Item doGet = httpClint.doGet(url01, Item.class);
		System.out.println("-----   ------  ------->Item doGet:"+doGet.toString());
		
		System.out.println(result);
	}
	
}













