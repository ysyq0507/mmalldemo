package com.mmall.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;

@Controller
@RequestMapping(value="/user/")
public class UserController {
	
	
	
	@Autowired
	private  IUserService iUserService;
	/**
	 * �û���¼
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> login(String username,String password,HttpSession session) {
		//service-->mybatis-->dao
		ServerResponse<User> response = iUserService.login(username, password);
		if(response.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
	}
	/**
	 * �˳���¼
	 * @param session
	 * @return
	 */
	@RequestMapping(value="logout.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> logout(HttpSession session){
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess();
	}
	
	@RequestMapping(value="register.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> register(User user){
		return iUserService.register(user);
	}
	
	@RequestMapping(value="check_valid.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> checkValid(String str,String tpye){
		return iUserService.checkValid(str, tpye);
	}
	
	
	
	
	
	
	
}


















