package com.marriage.demo.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.marriage.demo.entity.UserEntity;
import com.marriage.demo.service.UserServiceDemo;

@Controller
@RequestMapping("/demo")
public class UserControllerDemo {
	@Autowired
	UserServiceDemo userServiceDemo;
	
	@RequestMapping("/gg")
	@ResponseBody
	public String gg(){ 
		String ui = "OK";
		Map<String,String> maps = new HashMap();  
		maps.put("ObjectList", ui);
		JSONArray jsons = new JSONArray();
		jsons.add(maps);
		return jsons.toJSONString();
	}
	/**
	 * 查询用户列表 
	 * map内：page size 分页用
	 */
	@RequestMapping(value="/selectAll",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> selectAllUser(@RequestParam Map<String,Object> map){
		Map<String, Object> replyMap = new HashMap<String,Object>();
		
		if(map.get("page") != null && map.get("size") != null){
			int page = Integer.parseInt(map.get("page").toString());
			int size = Integer.parseInt(map.get("size").toString());
			int start = size*(page-1);
			map.put("start", start);
			map.put("page", page);
			map.put("size", size);
			
			List<UserEntity> userList = null;
			userList = userServiceDemo.selectAllUser(map);
			replyMap.put("userList", userList);
		}
		
		
		
		return replyMap;
	}
	
	/**
	 * 前台用户登录 
	 * map内：用户名和密码
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam Map<String,Object> map){
		Map<String, Object> replyMap = new HashMap<String,Object>();
		try {
			if(map.get("userPhone") != null && map.get("userPassword") != null){
				UserEntity  loginUser = userServiceDemo.oneLoginUsers(map);
				replyMap.put("loginUser", loginUser);
				replyMap.put("code", "200");
				replyMap.put("state", "suuess");
			}
		} catch (Exception e) {
				replyMap.put("code", "500");
				replyMap.put("state", "error");
		}
		
		return replyMap;
	}
	
	
	
}
