package com.marriage.message.entity;

import com.marriage.base.entity.BaseEntity;

public class MessageEntity extends BaseEntity{
	
    private Integer messageId;
    private String userName;
    private String userPhone;
    private String  surname;
    private String userEmail;
    private String userPostalCode;
    private String userAddress;
    private String content;

    private String user_name;

	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPostalCode() {
		return userPostalCode;
	}
	public void setUserPostalCode(String userPostalCode) {
		this.userPostalCode = userPostalCode;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

    
}
