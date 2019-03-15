package com.marriage.message.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marriage.message.dao.MessageDao;
import com.marriage.message.entity.MessageEntity;
import com.marriage.message.service.MessageService;
import com.marriage.user.dao.UserDao;


@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	
	@Autowired
    MessageDao messagedao;
    
    @Autowired
    UserDao userdao;

	@Override
	public <T> List<T> findMessageList(Map<String, Object>Map) throws RuntimeException {
		// TODO Auto-generated method stub
		return messagedao.findMessageList(Map);
	}

	@Override
	public MessageEntity findMessageById(Map<String, Object> Map) {
		// TODO Auto-generated method stub
		return messagedao.findMessageById(Map);
	}

	@Override
	public int delSingleMessage(Map<String, Object> Map) {
		// TODO Auto-generated method stub
		return messagedao.delSingleMessage(Map);
	}

	@Override
	public int delMoreMessage(Map<String, Object> Map) {
		// TODO Auto-generated method stub
		return messagedao.delMoreMessage(Map);
	}

	@Override
	public <T> List<T> updateMessageList(Map<String, Object>Map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer addUserLiuYan(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return messagedao.addUserLiuYan(map);
	}



	

}
