package com.marriage.news.service;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;

public interface NewsService {
	
	Integer addNews(Map<String, Object> map);

	List<ManInformation> selectNewsBypage(Map<String, Object> map);

	Integer updateNewsByNewsId(Map<String, Object> map);

	Integer deleteNewsById(Map<String, Object> map);

	List<ManInformation> selectNewsTopIndex(Map<String, Object> map);

	

}
