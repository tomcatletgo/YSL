package com.marriage.information.service;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;

public interface InformationService {

	Integer addWomanInformation(Map<String,Object> map);

	List<ManInformation> selectWomanInformationBypage(Map<String, Object> map);

	Integer updateWomanInformationByWomanId(Map<String, Object> map);

	Integer deleteWomanById(Map<String, Object> map);

}
