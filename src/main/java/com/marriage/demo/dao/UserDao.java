package com.marriage.demo.dao;

import java.util.List;
import java.util.Map;

import com.marriage.demo.entity.UserEntity;

public interface UserDao {

	List<UserEntity> selectAllUser(Map<String, Object> map);

}
