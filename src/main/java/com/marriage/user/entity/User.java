package com.marriage.user.entity;

import com.marriage.base.entity.BaseEntity;

public class User extends BaseEntity{
	/**
	 * 账号
	 */
	private String userName;
	private String password;
	private Integer character;
	private Integer activation;
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCharacter() {
		return character;
	}
	public void setCharacter(Integer character) {
		this.character = character;
	}
	public Integer getActivation() {
		return activation;
	}
	public void setActivation(Integer activation) {
		this.activation = activation;
	}
	
	
	
}
