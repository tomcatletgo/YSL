package com.marriage.news.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.set.MapBackedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marriage.news.service.NewsService;
import com.marriage.user.entity.ManInformation;

@Controller
@RequestMapping("news")
public class NewsController {

	@Autowired
	NewsService newsService;
	
	/**
	 * 
	 * @param map
	 *  新闻添加
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addNews(@RequestParam Map<String,Object> map){
		Map<String, Object> replyMap = new HashMap<String,Object>();
		int addnews = 0 ;
		try {
			addnews =  newsService.addNews(map);
			if(addnews > 0){
				replyMap.put("code", "200");
				replyMap.put("state", "success");
			}else{
				replyMap.put("code", "500");
				replyMap.put("state", "error");
			}
		} catch (Exception e) {
			// TODO: handle exception
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
		return replyMap;
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
	
	@RequestMapping(value="selectInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectNewsById(@RequestParam Map<String,Object> map){

		Map<String, Object> replyMap = new HashMap<String,Object>();
		
		//获取新闻详细数据
		if(map.get("newsId") != null){
			
			//接收的list
			List<ManInformation> newsinfo = null;
			newsinfo = newsService.selectNewsById(map);
			//放进map去
			replyMap.put("newsinfo", newsinfo);
			
		} else {
			replyMap.put("message", "page error");
		}
		
		
		
		return replyMap;

	}
	
	@RequestMapping(value="top")
	@ResponseBody
	public Map<String, Object> selectNewsTopIndex(@RequestParam Map<String,Object> map){

		Map<String, Object> replyMap = new HashMap<String,Object>();
		
		try {
			//接收的list
			List<ManInformation> newsList = null;
			newsList = newsService.selectNewsTopIndex(map);
			
			if(newsList.size() > 0){
				replyMap.put("code", "200");
				replyMap.put("state", "success");
				replyMap.put("newsList", newsList);
			}else{
				replyMap.put("code", "500");
				replyMap.put("state", "error");
			}
			
		} catch (Exception e) {
		
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
			
			
		
		
		
		return replyMap;

	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> updateNewsByNewsId(@RequestParam Map<String,Object> map){
		
		Map<String, Object> replyMap = new HashMap<String,Object>();

		
		int news = 0 ; 
		
		if (map.get("newsId") != null && map.size() > 1) {
			
			 news = newsService.updateNewsByNewsId(map);
		}
		
		if(news > 0){
			replyMap.put("code", "200");
			replyMap.put("state", "success");
		}else{
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
		return replyMap;
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