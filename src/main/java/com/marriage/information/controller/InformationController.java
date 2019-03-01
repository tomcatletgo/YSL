package com.marriage.information.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marriage.information.service.InformationService;


@Controller
@RequestMapping("/woman")
public class InformationController {
	
	@Autowired
	private InformationService informationService;
	
	
	
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)//TODO  post
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
}
