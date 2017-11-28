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
	 * ��¼service
	 */
	public ServerResponse<User> login(String username, String password) {
		// TODO Auto-generated method stub
		
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("�û���������");
		}
		//todo �����¼MD5
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		User user = userMapper.selectLogin(username, md5Password);
		if(user == null) {
			return ServerResponse.createByErrorMessage("�������");
		}
		
		user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);//�����ÿ�
		return ServerResponse.createBySuccess("��¼�ɹ�",user);
			
		
	}
	
	/**
	 * �û�ע��service
	 * @param user
	 * @return
	 */
	public ServerResponse<String> register(User user){
		
		ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
		if(!validResponse.isSuccess()) {
			return validResponse;
		}
		/*		
		 //��ʹ��У��ӿڵ�д��
		 int resultCount = userMapper.checkUsername(user.getUsername());
		 if(resultCount>0) {
			 return ServerResponse.createByErrorMessage("�û����Ѵ���");
		 }
		 */
		validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
		if(!validResponse.isSuccess()) {
			return validResponse;
		}
		/*
		 resultCount = userMapper.checkEmail(user.getEmail());
		 if(resultCount>0) {
			 return ServerResponse.createByErrorMessage("Email�Ѵ���");			 
		 }
		 */
		 user.setRole(Const.Role.ROLE_CUSTOMER);//��ɫ
		 //MD5����
		 user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		 
		 int resultCount =userMapper.insert(user);
		 if(resultCount==0) {
			 return ServerResponse.createByErrorMessage("ע��ʧ��");
		 }
		 return ServerResponse.createByErrorMessage("ע��ɹ�");
		 
	}

	
	/**
	 * У�� �û��� �����Ƿ���ں���
	 */
	public ServerResponse<String> checkValid(String str,String type){
		if(org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
			//��ʼУ��
			if(Const.USERNAME.equals(type)) {
				int resultCount = userMapper.checkUsername(str);
				if(resultCount>0) {
					return ServerResponse.createByErrorMessage("�û����Ѵ���");
				}
			}
			
			if(Const.EMAIL.equals(type)) {
				int resultCount = userMapper.checkEmail(str);
				if(resultCount>0) {
					return ServerResponse.createByErrorMessage("�����Ѵ���");
				}
			}
			
			
		}else {
			return ServerResponse.createByErrorMessage("��������");
		}
		return ServerResponse.createBySuccessMessage("У��ɹ�");
		
	}
	
	
	
	
	
	
	
	
	
	
	
}




















