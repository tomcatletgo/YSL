package com.marriage.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marriage.user.entity.ManInformation;
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
	@ResponseBody
	public Integer AddUser(@RequestParam Map<String,Object> map){
		Integer character = null;
		if(map.get("userCharacter")!=null){
			character = Integer.parseInt(map.get("userCharacter").toString());
		}
		//TODO 图片上传
		
		//url放进map.imgUrl TODO  测试
		
		
		if(character == 1){
			//调用添加男性方法
			return userService.addMan(map);
		} else if (character == 2){
			//调用添加女性方法  ..?
			//弃疗  扔进information那边  因为没账号
		}
		
		return 0;
	}
	
	
	/**
	 * 查寻男性信息  的第几页
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody 
	public Map<String,Object> selectManInformationByPage(@RequestParam Map<String,Object> map){
		Map<String, Object> replyMap = new HashMap<String,Object>();
		
		//页数
		if(map.get("page") != null && map.get("size") != null){
			//算页
			int page = Integer.parseInt(map.get("page").toString());
			int size = Integer.parseInt(map.get("size").toString());
			if (page <= 0 || size <= 0) {
				replyMap.put("message", "page error");
			}
			int start = size*(page-1);
			map.put("start", start);
			map.put("page", page);
			map.put("size", size);
			
			//接收的list
			List<ManInformation> manList = null;
			manList = userService.selectManInformationBypage(map);
			//放进map去
			replyMap.put("manList", manList);
			
		} else {
			replyMap.put("message", "page error");
		}
		
		return replyMap;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Integer updateManInformationByUserId(@RequestParam Map<String,Object> map){
		
		if (map.get("userId") != null && map.size() > 1) {
			
			return userService.updateManInformationByUserId(map);
		}
		
		return 0;
	}
}
