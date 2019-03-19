package com.marriage.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marriage.news.dao.NewsDao;
import com.marriage.news.service.NewsService;
import com.marriage.user.entity.ManInformation;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	NewsDao newsDao;

	@Override
	public Integer addNews(Map<String, Object> map) {
		
		//TODO 大概也有添加图片
		
		return newsDao.insertNews(map);
	}

	@Override
	public List<ManInformation> selectNewsBypage(Map<String, Object> map) {

		return newsDao.selectNewsByPage(map);
	}

	@Override
	public Integer updateNewsByNewsId(Map<String, Object> map) {

		return newsDao.updateNewsByNewsId(map);
	}

	@Override
	public Integer deleteNewsById(Map<String, Object> map) {

		return newsDao.updateNewsDelFlagById(map);
	}

	@Override
	public List<ManInformation> selectNewsTopIndex(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return newsDao.selectNewsTopIndex(map);
	}

	@Override
	public List<ManInformation> selectNewsById(Map<String, Object> map) {
		return newsDao.selectNewsById(map);
	}

}
