package com.marriage.demo.entity;

import com.marriage.base.entity.BaseEntity;

public class UserEntity extends BaseEntity {
   private String memberId;
   private String userId;
   private String userName;
   private String userPassword;
   private String userEmail;
   private String userPhone;
   private String userIdNumber;//身份证号
   private String creditLimit;//信用额度
   
   private String memberTime;//到期时间
   private String memberName;
   private String memberPoints;//会员积分
   private String memberRegistrationDate;//注册时间

 //private MemberAuthenticationEntity memberAuthenticationEntity; //实名认证信息
   private MemberPointsLevelEntity memberPointsLevelEntity;//会员等级
   
   public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getUserPassword() {
	return userPassword;
}
public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}
public String getUserEmail() {
	return userEmail;
}
public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}
public String getUserPhone() {
	return userPhone;
}
public void setUserPhone(String userPhone) {
	this.userPhone = userPhone;
}
public String getUserIdNumber() {
	return userIdNumber;
}
public void setUserIdNumber(String userIdNumber) {
	this.userIdNumber = userIdNumber;
}
public String getCreditLimit() {
	return creditLimit;
}
public void setCreditLimit(String creditLimit) {
	this.creditLimit = creditLimit;
}

   
   
/*public MemberAuthenticationEntity getMemberAuthenticationEntity() {
	return memberAuthenticationEntity;
}
public void setMemberAuthenticationEntity(MemberAuthenticationEntity memberAuthenticationEntity) {
	this.memberAuthenticationEntity = memberAuthenticationEntity;
}*/
public MemberPointsLevelEntity getMemberPointsLevelEntity() {
	return memberPointsLevelEntity;
}
public void setMemberPointsLevelEntity(MemberPointsLevelEntity memberPointsLevelEntity) {
	this.memberPointsLevelEntity = memberPointsLevelEntity;
}

public String getMemberId() {
	return memberId;
}
public void setMemberId(String memberId) {
	this.memberId = memberId;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getMemberTime() {
	return memberTime;
}
public void setMemberTime(String memberTime) {
	this.memberTime = memberTime;
}
public String getMemberName() {
	return memberName;
}
public void setMemberName(String memberName) {
	this.memberName = memberName;
}
public String getMemberPoints() {
	return memberPoints;
}
public void setMemberPoints(String memberPoints) {
	this.memberPoints = memberPoints;
}
public String getMemberRegistrationDate() {
	return memberRegistrationDate;
}
public void setMemberRegistrationDate(String memberRegistrationDate) {
	this.memberRegistrationDate = memberRegistrationDate;
}

}
