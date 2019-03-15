package com.marriage.message.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.marriage.message.entity.MessageEntity;

public interface MessageService {
	
	public <T> List<T> findMessageList(Map<String,Object>Map);
	
	public MessageEntity findMessageById(Map<String,Object>Map);
	
	public int delSingleMessage(Map<String,Object>Map);
	
	public int delMoreMessage(Map<String,Object>Map);
	
	public <T> List<T> updateMessageList(Map<String,Object>Map);
	
	Integer addUserLiuYan(Map<String,Object> map);

	
}
