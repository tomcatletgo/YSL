package com.marriage.img.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marriage.img.dao.ImgDao;
import com.marriage.img.service.ImgService;

@Service
@Transactional
public class ImgServiceImpl implements ImgService {

	@Autowired
	private ImgDao imgDao;
	
	@Override
	public Integer addUserImg(Map<String, Object> map) {
		
		
		return imgDao.addUserImg(map);
	}

}
