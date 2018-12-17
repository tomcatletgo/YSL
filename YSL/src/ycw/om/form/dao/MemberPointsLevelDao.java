package ycw.om.form.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ycw.om.form.entity.MemberPointsLevelEntity;

@Repository
public interface MemberPointsLevelDao {

	List<MemberPointsLevelEntity> getLevelList();
	
}
