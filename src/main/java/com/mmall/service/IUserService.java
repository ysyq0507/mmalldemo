package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {
	
	ServerResponse<User> login(String username,String password);//��¼
	ServerResponse<String> register(User user);//ע��
	ServerResponse<String> checkValid(String str,String tpye);//У��
}
