package com.marriage.user.entity;

import com.marriage.base.entity.BaseEntity;
import com.marriage.information.entity.WomanInformation;

public class User extends BaseEntity{
	/**
	 * 账号
	 */
	private String userName;
	private String password;
	/**
	 *  0 admin
	 *  1 man
	 *  2 woman（没了
	 */
	private Integer userCharacter;
	private Integer activation;
	
	/**
	 * if is a man,  this use have this attribute
	 */
	private ManInformation manInformation;
	
	
	
	
	public ManInformation getManInformation() {
		return manInformation;
	}
	public void setManInformation(ManInformation manInformation) {
		this.manInformation = manInformation;
	}
	
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
	
	public Integer getUserCharacter() {
		return userCharacter;
	}
	public void setUserCharacter(Integer userCharacter) {
		this.userCharacter = userCharacter;
	}
	public Integer getActivation() {
		return activation;
	}
	public void setActivation(Integer activation) {
		this.activation = activation;
	}
	
	
	
}
