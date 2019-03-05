package com.marriage.user.service;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;

public interface UserService {

	Integer addMan(Map<String, Object> map);

	List<ManInformation> selectManInformationBypage(Map<String, Object> map);

	Integer updateManInformationByUserId(Map<String, Object> map);

}
