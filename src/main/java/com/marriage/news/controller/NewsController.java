package com.marriage.news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.set.MapBackedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marriage.news.service.NewsService;
import com.marriage.user.entity.ManInformation;

@Controller
@RequestMapping("news")
public class NewsController {

	@Autowired
	NewsService newsService;
	
	
	@RequestMapping(value="add")
	@ResponseBody
	public Integer addNews(@RequestParam Map<String,Object> map){
		
		//上传图片 TODO  
		
		return newsService.addNews(map);
	}
	
	
	@RequestMapping(value="select")
	@ResponseBody
	public Map<String, Object> selectNewsByPage(@RequestParam Map<String,Object> map){

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
			List<ManInformation> newsList = null;
			newsList = newsService.selectNewsBypage(map);
			//放进map去
			replyMap.put("newsList", newsList);
			
		} else {
			replyMap.put("message", "page error");
		}
		
		
		
		return replyMap;

	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Integer updateNewsByNewsId(@RequestParam Map<String,Object> map){
		
		if (map.get("newsId") != null && map.size() > 1) {
			
			return newsService.updateNewsByNewsId(map);
		}
		
		return 0;
	}
	
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public Integer deleteNewsById(@RequestParam Map<String,Object> map){
		
		//大概需要取个数组 放 map.ids
		
		if (map.get("newsId") != null || map.get("ids") != null) {
			
			//id同样当做数组放进ids里
			if (map.get("newsId") != null) {
				
				String id = map.get("newsId").toString();
				map.put("ids", id);
			}
			
			
			return newsService.deleteNewsById(map);
		}
		
		return 0;
	}
	
}