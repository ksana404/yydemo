package com.yy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yy.pojo.User;
import com.yy.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api	//为当前类生成API
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//查询全部用户信息
	@GetMapping("/findAll")
	@ApiOperation(value = "查询用户全部信息",notes ="你TM是小学文化吗,这都读不懂???")
	@ApiResponses({@ApiResponse(code = 401,message = "用户操作服务器时,用户没有认证"),
		@ApiResponse(code = 403,message = "服务响应异常!!!"),
		@ApiResponse(code = 404,message = "你连404都不知道?????? 回家吧,别浪费资源 滚犊子")
		})
	public List<User> findAll(){
		
		return userService.findAll();
	}
	
	@GetMapping("/user/{id}")
	@ApiOperation(value = "根据id查询用户信息",notes ="你TM是小学文化吗,这都读不懂???")
	@ApiResponses({@ApiResponse(code = 401,message = "用户操作服务器时,用户没有认证"),
		@ApiResponse(code = 403,message = "服务响应异常!!!"),
		@ApiResponse(code = 404,message = "你连404都不知道?????? 回家吧,别浪费资源 滚犊子")
		})
	public User findOne(@PathVariable Integer id) {
		
		return userService.findUserById(id);
	}
	
	@DeleteMapping("/user/{id}")
	@ApiOperation(value = "根据id删除用户信息",notes ="你TM是小学文化吗,这都读不懂???")
	public String deleteUserById(@PathVariable Integer id) {
		
		userService.deleteUser(id);
		return "删除数据成功!!!!";
	}
	
	
	
	
	/*
	 * @GetMapping("/findAll")
	 * 
	 * @ApiOperation(value="查询全部用户信息",notes="查询数据库全部的用户信息")
	 * 
	 * @ApiResponses({
	 * 
	 * @ApiResponse(code=401,message="请求需要认证"),
	 * 
	 * @ApiResponse(code=403,message="服务器拒绝请求"),
	 * 
	 * @ApiResponse(code=404,message="服务器获取资源失败"), }) public List<User> findAll(){
	 * 
	 * return userService.findAll(); }
	 * 
	 * @GetMapping("/user/{id}")
	 * 
	 * @ApiOperation(value="根据ID查询用户信息")
	 * 
	 * @ApiResponses({
	 * 
	 * @ApiResponse(code=401,message="请求需要认证"),
	 * 
	 * @ApiResponse(code=403,message="服务器拒绝请求"),
	 * 
	 * @ApiResponse(code=404,message="服务器获取资源失败"), }) public User
	 * findUserById(@PathVariable Integer id) {
	 * 
	 * return userService.findUserById(id); }
	 * 
	 * @PostMapping("/user/{name}/{age}/{sex}")
	 * 
	 * @ApiOperation(value="新增用户") public String saveUser(User user) {
	 * 
	 * userService.saveUser(user); return "用户新增成功!!!"; }
	 * 
	 * @DeleteMapping("/user/{id}")
	 * 
	 * @ApiOperation(value="删除用户") public String deleteUser(@PathVariable Integer
	 * id) {
	 * 
	 * userService.deleteUser(id); return "删除用户成功!!!"; }
	 * 
	 * @PutMapping("/user/{id}/{name}/{age}/{sex}")
	 * 
	 * @ApiOperation(value="更新用户") public String updateUser(User user) {
	 * 
	 * userService.updateUser(user); return "更新用户成功!!!!"; }
	 */
	
	
}
