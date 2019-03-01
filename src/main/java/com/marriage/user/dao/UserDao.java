package com.marriage.user.dao;

import java.util.Map;

public interface UserDao {

	Integer addUser(Map<String, Object> map);

	Integer addManInformation(Map<String, Object> map);

}
