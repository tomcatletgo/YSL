package com.marriage.demo.entity;

import com.marriage.base.entity.BaseEntity;

/**
 * @author 曹宇安
 *
 * @Description 会员等级
 */
public class MemberPointsLevelEntity extends BaseEntity{

	private String memberLevelName;
	private String memberPointsBottom;
	private String memberPointsTop;
	private Double memberDiscount;
	private Integer isSpecial;
	private Integer isShowPrice;
	
	
	public String getMemberLevelName() {
		return memberLevelName;
	}
	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
	public String getMemberPointsBottom() {
		return memberPointsBottom;
	}
	public void setMemberPointsBottom(String memberPointsBottom) {
		this.memberPointsBottom = memberPointsBottom;
	}
	public String getMemberPointsTop() {
		return memberPointsTop;
	}
	public void setMemberPointsTop(String memberPointsTop) {
		this.memberPointsTop = memberPointsTop;
	}
	public Double getMemberDiscount() {
		return memberDiscount;
	}
	public void setMemberDiscount(Double memberDiscount) {
		this.memberDiscount = memberDiscount;
	}
	public Integer getIsSpecial() {
		return isSpecial;
	}
	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}
	public Integer getIsShowPrice() {
		return isShowPrice;
	}
	public void setIsShowPrice(Integer isShowPrice) {
		this.isShowPrice = isShowPrice;
	}
	
	
	
}
