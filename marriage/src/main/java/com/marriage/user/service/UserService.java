package com.marriage.user.service;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;
import com.marriage.user.entity.User;

public interface UserService {

	Integer addMan(Map<String, Object> map);

	List<ManInformation> selectManInformationBypage(Map<String, Object> map);

	Integer updateManInformationByUserId(Map<String, Object> map);

	Integer deleteUserById(Map<String, Object> map);

	Integer checkUserNameIsUsedOrNot(String userName);

	User userLogin(Map<String, Object> map);

	int userAdds(Map<String, Object> map);

}
