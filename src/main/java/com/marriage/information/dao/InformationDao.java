package com.marriage.information.dao;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;

public interface InformationDao {

	Integer addWomanInformation(Map<String, Object> map);

	List<ManInformation> selectWomanInformationBypage(Map<String, Object> map);

}
