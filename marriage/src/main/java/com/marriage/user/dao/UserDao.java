package com.marriage.user.dao;

import java.util.List;
import java.util.Map;

import com.marriage.user.entity.ManInformation;
import com.marriage.user.entity.User;

public interface UserDao {

	Integer addUser(Map<String, Object> map);

	Integer addManInformation(Map<String, Object> map);

	List<ManInformation> selectManInformationByPage(Map<String, Object> map);

	Integer updateManInformationByUserId(Map<String, Object> map);

	Integer updateUserDelFlagByIds(Map<String, Object> map);

	List<User> selectUserByUserName(String userName);

	List<User> userLogin(Map<String, Object> map);

	Integer userAdds(Map<String, Object> map);
	//添加到user表
	Integer userAddsUser(Map<String, Object> map);

	User userUserId(Map<String, Object> map);


}
