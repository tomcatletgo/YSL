package com.marriage.user.entity;

import com.marriage.base.entity.BaseEntity;
import com.marriage.information.entity.ManInformation;
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
	 *  2 woman
	 */
	private Integer character;
	private Integer activation;
	
	/**
	 * if is a man,  this use have this attribute
	 */
	private ManInformation manInformation;
	/**
	 * if is a woman,  this use have this attribute
	 */
	private WomanInformation womanInformation;
	
	
	
	public ManInformation getManInformation() {
		return manInformation;
	}
	public void setManInformation(ManInformation manInformation) {
		this.manInformation = manInformation;
	}
	public WomanInformation getWomanInformation() {
		return womanInformation;
	}
	public void setWomanInformation(WomanInformation womanInformation) {
		this.womanInformation = womanInformation;
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
