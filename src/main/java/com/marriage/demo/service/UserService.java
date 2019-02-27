package com.marriage.demo.service;

import java.util.List;
import java.util.Map;

import com.marriage.demo.entity.UserEntity;

public interface UserService {

	public List<UserEntity> selectAllUser(Map<String, Object> map);
	
}
