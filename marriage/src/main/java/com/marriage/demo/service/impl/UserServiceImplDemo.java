package com.marriage.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriage.demo.dao.UserDaoDemo;
import com.marriage.demo.entity.UserEntity;
import com.marriage.demo.service.UserServiceDemo;

@Service
public class UserServiceImplDemo implements UserServiceDemo {
	
	@Autowired
	private UserDaoDemo userDaoDemo;

	@Override
	public List<UserEntity> selectAllUser(Map<String, Object> map) {
		
		 List<UserEntity> list = userDaoDemo.selectAllUser(map); 
		
		return list;
	}

	@Override
	public UserEntity oneLoginUsers(Map<String, Object> map) {
		UserEntity oneUser = userDaoDemo.oneLoginUsers(map);
		return oneUser;
	}

}
