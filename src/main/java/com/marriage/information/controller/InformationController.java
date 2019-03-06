package com.marriage.information.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marriage.information.service.InformationService;
import com.marriage.user.entity.ManInformation;


@Controller
@RequestMapping("/woman")
public class InformationController {
	
	@Autowired
	private InformationService informationService;
	
	
	
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)//TODO  post
	@ResponseBody
	public Integer addWomanInformation(@RequestParam Map<String,Object> map){
		int num = 0;
		//如果有avatar上传头像 TODO 
		
		//放进map.avatar
		
		
		if (map.containsKey("trueName") && map.containsKey("country")) {
			num = informationService.addWomanInformation(map);
		}
		
		return num;
	}
	
	
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectWomanInformationByPage(@RequestParam Map<String,Object> map){
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
			List<ManInformation> womanList = null;
			womanList = informationService.selectWomanInformationBypage(map);
			//放进map去
			replyMap.put("womanList", womanList);
			
		} else {
			replyMap.put("message", "page error");
		}
		
		
		
		return replyMap;
	}
	
	
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Integer updateWomanInformationByWomanId(@RequestParam Map<String,Object> map){
		
		if (map.get("womanId") != null && map.size() > 1) {
			
			return informationService.updateWomanInformationByWomanId(map);
		}
		
		return 0;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public Integer deleteWomanById(@RequestParam Map<String,Object> map){
		
		//大概需要取个数组 放 map.ids
		
		if (map.get("userId") != null || map.get("ids") != null) {
			
			//id同样当做数组放进ids里
			if (map.get("userId") != null) {
				
				String id = map.get("userId").toString();
				map.put("ids", id);
			}
			
			
			return informationService.deleteWomanById(map);
		}
		
		return 0;
	}
	
}
