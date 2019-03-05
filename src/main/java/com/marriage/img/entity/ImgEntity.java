package com.marriage.img.entity;

import com.marriage.base.entity.BaseEntity;

public class ImgEntity extends BaseEntity {

	private Integer imgId;
	private Integer userId;
	private Integer womanId;
	private String imgUrl;
	
	
	
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getWomanId() {
		return womanId;
	}
	public void setWomanId(Integer womanId) {
		this.womanId = womanId;
	}
	
	
	
	
	
}
