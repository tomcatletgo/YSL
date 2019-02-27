package com.marriage.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriage.demo.dao.UserDao;
import com.marriage.demo.entity.UserEntity;
import com.marriage.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserEntity> selectAllUser(Map<String, Object> map) {
		
		 List<UserEntity> list = userDao.selectAllUser(map); 
		
		return list;
	}

}
