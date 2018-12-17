package ycw.om.form.service;

import java.util.List;
import java.util.Map;

import ycw.om.form.entity.MemberPointsLevelEntity;

public interface MemberPointsLevelService {

	/**
	 * 
	 * @param map
	 * @return List<MemberPointsLevelEntity>
	 */
	List<MemberPointsLevelEntity> getLevelList(Map<String, Object> map);

}
