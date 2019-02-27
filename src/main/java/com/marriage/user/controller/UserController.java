package com.marriage.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marriage.user.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	
	@RequestMapping("/gg")
	@ResponseBody
	public String gg(){
		
		return "gg";
	}
	
	/**
	 * 添加用户 返回0或1
	 * @不会添加管理员账户
	 */
	@RequestMapping(value="/adduser",method=RequestMethod.GET)
	public Integer AddUser(@RequestParam Map<String,Object> map){
		Integer character = Integer.parseInt(map.get("character").toString());
		if(character == 1){
			//调用添加男性方法
			return userService.addMan(map);
		} else if (character == 2){
			//调用添加女性方法
		}
		
		return 0;
	}
}
