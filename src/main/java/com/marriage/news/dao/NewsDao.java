package com.marriage.news.dao;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;

public interface NewsDao {

	Integer insertNews(Map<String, Object> map);

	List<ManInformation> selectNewsByPage(Map<String, Object> map);

	Integer updateNewsByNewsId(Map<String, Object> map);

	Integer updateNewsDelFlagById(Map<String, Object> map);

}
