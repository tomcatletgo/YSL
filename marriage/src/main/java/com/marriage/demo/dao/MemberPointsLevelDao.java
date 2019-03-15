package com.marriage.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.marriage.demo.entity.MemberPointsLevelEntity;


@Repository
public interface MemberPointsLevelDao {

	List<MemberPointsLevelEntity> getLevelList();
	
}
