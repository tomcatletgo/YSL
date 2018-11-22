package ycw.om.form.entity;

public class MemberEntity extends BaseEntity {
   private String memberId;
   private String userId;
   private String memberTime;
   private String memberName;
   private String memberPoints;
   private String memberRegistrationDate;

   
   private MemberAuthenticationEntity memberAuthenticationEntity;
   private MemberPointsLevelEntity memberPointsLevelEntity;
   
   
public MemberAuthenticationEntity getMemberAuthenticationEntity() {
	return memberAuthenticationEntity;
}
public void setMemberAuthenticationEntity(MemberAuthenticationEntity memberAuthenticationEntity) {
	this.memberAuthenticationEntity = memberAuthenticationEntity;
}
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
