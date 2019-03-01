package com.marriage.information.entity;

import java.util.List;

import com.marriage.base.entity.BaseEntity;
import com.marriage.img.entity.ImgEntity;
import com.marriage.user.entity.User;

public class WomanInformation extends BaseEntity {
	/**
	 * user 对象
	 */
	private User user;
	/**
	 * imgs
	 */
	private List<ImgEntity> imgs;
	
	private Integer userId;
	/**
	 * 头像图片地址
	 */
	private String avatar;
	private String trueName;
	private Integer country;
	/**
	 * 用户地址
	 */
	private String address;
	private String phone;
	private String idCard;
	private String birthDate;
	private String heightAndWeight;
	private String identityGuarantorTrueName;
	private String identityGuarantorPhone;
	private String identityGuarantorAddress;
	/**
	 * 关系
	 */
	private String identityGuarantorRelationshipWithUser;
	private String marriageHistory;
	/**
	 * 职业
	 */
	private String occupation;
	private String bloodGroup;
	private String womanCharacter;
	private String moveToJapan;
	/**
	 * 酒 烟草
	 */
	private String smokingSake;
	private String japaneseLevel;
	private String interest;
	private String cookingLevel;
	private String familyMembers;
	private String hopeMaleAge;
	private String hopeMaleHeightWeight;
	private String hopeMaleMarriageHistory;
	private String hopeMaleChild;
	private String hopeMaleEducationLevel;
	/**
	 * 年收入
	 */
	private String hopeMaleAnnualIncome;
	/**
	 * 可同居/不可
	 */
	private String hopeMaleCanCohabitation;
	private String hopeMaleOccupation;
	/**
	 * 留言
	 */
	private String messageToMale;
	
	
	
	
	
	
	
	
	
	public List<ImgEntity> getImgs() {
		return imgs;
	}
	public void setImgs(List<ImgEntity> imgs) {
		this.imgs = imgs;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public void setHopeMaleAge(String hopeMaleAge) {
		this.hopeMaleAge = hopeMaleAge;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getHeightAndWeight() {
		return heightAndWeight;
	}
	public void setHeightAndWeight(String heightAndWeight) {
		this.heightAndWeight = heightAndWeight;
	}
	public String getIdentityGuarantorTrueName() {
		return identityGuarantorTrueName;
	}
	public void setIdentityGuarantorTrueName(String identityGuarantorTrueName) {
		this.identityGuarantorTrueName = identityGuarantorTrueName;
	}
	public String getIdentityGuarantorPhone() {
		return identityGuarantorPhone;
	}
	public void setIdentityGuarantorPhone(String identityGuarantorPhone) {
		this.identityGuarantorPhone = identityGuarantorPhone;
	}
	public String getIdentityGuarantorAddress() {
		return identityGuarantorAddress;
	}
	public void setIdentityGuarantorAddress(String identityGuarantorAddress) {
		this.identityGuarantorAddress = identityGuarantorAddress;
	}
	public String getIdentityGuarantorRelationshipWithUser() {
		return identityGuarantorRelationshipWithUser;
	}
	public void setIdentityGuarantorRelationshipWithUser(String identityGuarantorRelationshipWithUser) {
		this.identityGuarantorRelationshipWithUser = identityGuarantorRelationshipWithUser;
	}
	public String getMarriageHistory() {
		return marriageHistory;
	}
	public void setMarriageHistory(String marriageHistory) {
		this.marriageHistory = marriageHistory;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	public String getWomanCharacter() {
		return womanCharacter;
	}
	public void setWomanCharacter(String womanCharacter) {
		this.womanCharacter = womanCharacter;
	}
	public String getHopeMaleAge() {
		return hopeMaleAge;
	}
	public String getMoveToJapan() {
		return moveToJapan;
	}
	public void setMoveToJapan(String moveToJapan) {
		this.moveToJapan = moveToJapan;
	}
	public String getSmokingSake() {
		return smokingSake;
	}
	public void setSmokingSake(String smokingSake) {
		this.smokingSake = smokingSake;
	}
	public String getJapaneseLevel() {
		return japaneseLevel;
	}
	public void setJapaneseLevel(String japaneseLevel) {
		this.japaneseLevel = japaneseLevel;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getCookingLevel() {
		return cookingLevel;
	}
	public void setCookingLevel(String cookingLevel) {
		this.cookingLevel = cookingLevel;
	}
	public String getFamilyMembers() {
		return familyMembers;
	}
	public void setFamilyMembers(String familyMembers) {
		this.familyMembers = familyMembers;
	}
	
	public String getHopeMaleHeightWeight() {
		return hopeMaleHeightWeight;
	}
	public void setHopeMaleHeightWeight(String hopeMaleHeightWeight) {
		this.hopeMaleHeightWeight = hopeMaleHeightWeight;
	}
	public String getHopeMaleMarriageHistory() {
		return hopeMaleMarriageHistory;
	}
	public void setHopeMaleMarriageHistory(String hopeMaleMarriageHistory) {
		this.hopeMaleMarriageHistory = hopeMaleMarriageHistory;
	}
	public String getHopeMaleChild() {
		return hopeMaleChild;
	}
	public void setHopeMaleChild(String hopeMaleChild) {
		this.hopeMaleChild = hopeMaleChild;
	}
	public String getHopeMaleEducationLevel() {
		return hopeMaleEducationLevel;
	}
	public void setHopeMaleEducationLevel(String hopeMaleEducationLevel) {
		this.hopeMaleEducationLevel = hopeMaleEducationLevel;
	}
	public String getHopeMaleAnnualIncome() {
		return hopeMaleAnnualIncome;
	}
	public void setHopeMaleAnnualIncome(String hopeMaleAnnualIncome) {
		this.hopeMaleAnnualIncome = hopeMaleAnnualIncome;
	}
	public String getHopeMaleCanCohabitation() {
		return hopeMaleCanCohabitation;
	}
	public void setHopeMaleCanCohabitation(String hopeMaleCanCohabitation) {
		this.hopeMaleCanCohabitation = hopeMaleCanCohabitation;
	}
	public String getHopeMaleOccupation() {
		return hopeMaleOccupation;
	}
	public void setHopeMaleOccupation(String hopeMaleOccupation) {
		this.hopeMaleOccupation = hopeMaleOccupation;
	}
	public String getMessageToMale() {
		return messageToMale;
	}
	public void setMessageToMale(String messageToMale) {
		this.messageToMale = messageToMale;
	}
}
