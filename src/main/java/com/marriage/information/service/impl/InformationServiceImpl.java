package com.marriage.information.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marriage.information.dao.InformationDao;
import com.marriage.information.service.InformationService;


@Service
@Transactional
public class InformationServiceImpl implements InformationService {

	@Autowired
	private InformationDao informationDao;
	
	@Override
	public Integer addWomanInformation(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return informationDao.addWomanInformation(map);
	}

}
