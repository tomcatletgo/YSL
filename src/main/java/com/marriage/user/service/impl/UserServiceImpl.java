package com.marriage.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriage.information.entity.ManInformation;
import com.marriage.information.service.InformationService;
import com.marriage.user.dao.UserDao;
import com.marriage.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired 
	private InformationService informationService;
	
	@Override
	public Integer addMan(Map<String, Object> map) {

		//添加账号 返回id进map
		int num = userDao.addUser(map);
		//添加information 
		//informationService.addManInformation();
		System.out.println(map.get("userId").toString()+"hosjidsgga'ngwe2034rikpwqf, p023u t");
		
		return num;
	}

}
