package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {
	
	ServerResponse<User> login(String username,String password);//µÇÂ¼
	ServerResponse<String> register(User user);//×¢²á
	ServerResponse<String> checkValid(String str,String tpye);//Ð£Ñé
}
