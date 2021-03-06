package com.mmall.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.controller.util.MD5Util;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

	
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * 登录service
	 */
	public ServerResponse<User> login(String username, String password) {
		// TODO Auto-generated method stub
		
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		//todo 密码登录MD5
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		User user = userMapper.selectLogin(username, md5Password);
		if(user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		
		user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);//密码置空
		return ServerResponse.createBySuccess("登录成功",user);
			
		
	}
	
	/**
	 * 用户注册service
	 * @param user
	 * @return
	 */
	public ServerResponse<String> register(User user){
		
		ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
		if(!validResponse.isSuccess()) {
			return validResponse;
		}
		/*		
		 //不使用校验接口的写法
		 int resultCount = userMapper.checkUsername(user.getUsername());
		 if(resultCount>0) {
			 return ServerResponse.createByErrorMessage("用户名已存在");
		 }
		 */
		validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
		if(!validResponse.isSuccess()) {
			return validResponse;
		}
		/*
		 resultCount = userMapper.checkEmail(user.getEmail());
		 if(resultCount>0) {
			 return ServerResponse.createByErrorMessage("Email已存在");			 
		 }
		 */
		 user.setRole(Const.Role.ROLE_CUSTOMER);//角色
		 //MD5加密
		 user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		 
		 int resultCount =userMapper.insert(user);
		 if(resultCount==0) {
			 return ServerResponse.createByErrorMessage("注册失败");
		 }
		 return ServerResponse.createByErrorMessage("注册成功");
		 
	}

	
	/**
	 * 校验 用户名 邮箱是否存在函数
	 */
	public ServerResponse<String> checkValid(String str,String type){
		if(org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
			//开始校验
			if(Const.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUsername(str);
				if(resultCount>0) {
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			}
			
			if(Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if(resultCount>0) {
					return ServerResponse.createByErrorMessage("邮箱已存在");
				}
			}
			
			
		}else {
			return ServerResponse.createByErrorMessage("参数错误");
		}
		return ServerResponse.createBySuccessMessage("校验成功");
		
	}
	
	
	
	
	
	
	
	
	
	
	
}




















