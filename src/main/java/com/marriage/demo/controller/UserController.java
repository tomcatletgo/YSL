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

import com.marriage.demo.entity.UserEntity;
import com.marriage.demo.service.UserService;

@Controller
@RequestMapping("/demo")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/gg")
	@ResponseBody
	public String gg(){
		
		return "gg";
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
			userList = userService.selectAllUser(map);
			replyMap.put("userList", userList);
		}
		
		
		
		return replyMap;
	}
	
}
