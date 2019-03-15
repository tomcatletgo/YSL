package com.marriage.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marriage.demo.entity.UserEntity;
import com.marriage.message.entity.MessageEntity;
import com.marriage.message.service.MessageService;
import com.marriage.user.entity.ManInformation;

@Controller
@RequestMapping("/messageController")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	
	@RequestMapping(params="messagelist")
    public ModelAndView messagelist(){
		ModelAndView modelandview= new ModelAndView();
		
		modelandview.setViewName("");
		return modelandview;
	}
	@RequestMapping(value="findfindMessageList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findMessageList(@RequestParam Map<String,Object>map){
            Map<String, Object> pMap = new HashMap<String,Object>();
		
		if(map.get("page") != null && map.get("size") != null){
			int page = Integer.parseInt(map.get("page").toString());
			int size = Integer.parseInt(map.get("size").toString());
			int start = size*(page-1);
			map.put("start", start);
			map.put("page", page);
			map.put("size", size);
			
			List<UserEntity> userList = null;
			userList = messageService.findMessageList(pMap);
			pMap.put("userList", userList);
		}
		
		
		
		return pMap;
		
	}
	@RequestMapping(value="findMessageById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> findMessageById(@RequestParam Map<String,Object> map){
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
			MessageEntity messageEntity = null;
			messageEntity = messageService.findMessageById(map);
			//放进map去
			replyMap.put("messageEntity", messageEntity);
			
		} else {
			replyMap.put("message", "page error");
		}
		
		return replyMap; 
}
	@RequestMapping(value="/delSingleMessage")
	@ResponseBody
	public Integer delSingleMessage(@RequestParam Map<String,Object> map){
		
		//大概需要取个数组 放 map.ids
		
		if (map.get("messageId") != null || map.get("ids") != null) {
			
			//id同样当做数组放进ids里
			if (map.get("messageId") != null) {
				
				String id = map.get("messageId").toString();
				map.put("ids", id);
			}
			
			
			return messageService.delSingleMessage(map);
		}
		
		return 0;
	}
	@RequestMapping(value="/delMoreMessage")
	@ResponseBody
	public Integer delMoreMessage(@RequestParam Map<String,Object> map){
		
		//大概需要取个数组 放 map.ids
		
		if (map.get("messageId") != null || map.get("ids") != null) {
			
			//id同样当做数组放进ids里
			if (map.get("messageId") != null) {
				
				String id = map.get("messageId").toString();
				map.put("ids", id);
			}
			
			
			return messageService.delMoreMessage(map);
		}
		
		return 0;
	}
	
	/**
	 * auod wangdiran   5 资讯版本  1     接口地址   前端用户添加信息接口
	 * 
	 * 
	 * **/
	@RequestMapping(value="/addUserLy",method=RequestMethod.POST)//TODO  post
	@ResponseBody
	public Map<String, Object> addUserLiuYan(@RequestParam Map<String,Object> map,HttpServletRequest request){
		int num = 0;
		Map<String, Object> replyMap = new HashMap<String,Object>();
		if (map.containsKey("userName") && map.containsKey("userPhone") &&
			map.containsKey("userEmail") && map.containsKey("userPostalCode") && 
			map.containsKey("userAddress") && map.containsKey("content") ) {
			num = messageService.addUserLiuYan(map);
		}
		if(num > 0){
			replyMap.put("code", "200");
			replyMap.put("state", "success");
		}else{
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
		return replyMap;
	}
	
	
	

	
	
}
