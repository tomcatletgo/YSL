package com.marriage.demo.service;

import java.util.List;
import java.util.Map;

import com.marriage.demo.entity.MemberPointsLevelEntity;


public interface MemberPointsLevelService {

	/**
	 *  列表
	 * @param map
	 * @return List<MemberPointsLevelEntity>
	 */
	List<MemberPointsLevelEntity> getLevelList(Map<String, Object> map);
	
	

}
