package ycw.om.form.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.common.service.impl.CommonServiceImpl;
import ycw.om.form.dao.MemberPointsLevelDao;
import ycw.om.form.entity.MemberPointsLevelEntity;
import ycw.om.form.service.MemberPointsLevelService;

@Service
public class MemberPointsLevelServiceImpl extends CommonServiceImpl implements MemberPointsLevelService {

	//@Autowired
	MemberPointsLevelDao memberPointsLevelDao;
	
	@Override
	public List<MemberPointsLevelEntity> getLevelList(Map<String, Object> map) {
		
		List<MemberPointsLevelEntity> levelList = null;
		levelList = memberPointsLevelDao.getLevelList();
		
		return levelList;
	}
	
}
