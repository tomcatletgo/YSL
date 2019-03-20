package com.marriage.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriage.demo.dao.MemberPointsLevelDao;
import com.marriage.demo.entity.MemberPointsLevelEntity;
import com.marriage.demo.service.MemberPointsLevelService;


@Service
public class MemberPointsLevelServiceImpl implements MemberPointsLevelService {

	@Autowired
	MemberPointsLevelDao memberPointsLevelDao;
	
	@Override
	public List<MemberPointsLevelEntity> getLevelList(Map<String, Object> map) {
		
		List<MemberPointsLevelEntity> levelList = null;
		levelList = memberPointsLevelDao.getLevelList();
		
		return levelList;
	}
	
}
