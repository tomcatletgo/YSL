package com.marriage.user.entity;

import java.util.List;

import com.marriage.base.entity.BaseEntity;
import com.marriage.img.entity.ImgEntity;

public class ManInformation extends BaseEntity{
	
	/**
	 * 信息对应的用户对象
	 */
	private User user;
	/**
	 * imgs
	 */
	private List<ImgEntity> imgs;
	
	private Integer userId;
	private String trueName;
	/**
	 * 假名
	 */
	private String karigana;
	/**
	 * 罗马音
	 */
	private String romaji;
	/**
	 * 邮政编码
	 */
	private String postalCode;
	/**
	 * 都道府県(就当他是省)
	 */
	private Integer prefectures;
	/**
	 * 市区町村(就当他是市)
	 */
	private String municipalWard;
	/**
	 * 町名番地等(就当他是区)
	 */
	private String streetBunch;
	private String buildingName;
	/**
	 * 本籍(祖籍)
	 */
	private Integer ancestralHome;
	private String phone;
	private String mobilePhone;
	private String email;
	private String otherEmail;
	private String lineId;
	private String dateOfBirth;
	private Integer age;
	private String hobby;
	/**
	 * 資格(大概类似于有没有证书那样？)
	 */
	private String qualification;
	/**
	 * 0 not smoking 1 smoking
	 */
	private Integer smoking;
	/**
	 * 0 not drink 1 drink only work(?) 2 drink
	 */
	private Integer sake;
	private String height;
	private String weight;
	private String bloodGroup;
	/**
	 * 最終学歴
	 */
	private String education;
	/**
	 * 職業
	 */
	private String occupation;
	/**
	 * 年収(税込)
	 */
	private String annualIncome;
	/**
	 * 資産
	 */
	private String assets;
	private Integer holiday;
	/**
	 * お住まいの形態 
	 * 总之有5种情况 具体的不重要...
	 */
	private Integer residenceForm;
	/**
	 * 必須ご本人様の続柄  (大概就是家里排老几
	 * 1 2 3 4 ==> 长男 次男 三男 四男
	 */
	private Integer homeRowing;
	private String familyMembers;
	/**
	 *  0 first  1 not first
	 */
	private Integer marriageHistory;
	/**
	 * 結婚後の同居の有無
	 */
	private String livingTogether;
	/**
	 * 再婚の場合(人数？
	 */
	private Integer remarriageNum;
	/**
	 * 0 have 1 don't have 
	 */
	private Integer remarriageSituation;
	/**
	 * 離婚の時期と理由
	 */
	private String divorceReason;
	private String message;
	/**
	 * 備考
	 */
	private String remark;
	/*
	 * 写真
	 * */
	private String xzphotourl;
	
	
	
	
	
	public List<ImgEntity> getImgs() {
		return imgs;
	}
	public void setImgs(List<ImgEntity> imgs) {
		this.imgs = imgs;
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
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getKarigana() {
		return karigana;
	}
	public void setKarigana(String karigana) {
		this.karigana = karigana;
	}
	public String getRomaji() {
		return romaji;
	}
	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Integer getPrefectures() {
		return prefectures;
	}
	public void setPrefectures(Integer prefectures) {
		this.prefectures = prefectures;
	}
	public String getMunicipalWard() {
		return municipalWard;
	}
	public void setMunicipalWard(String municipalWard) {
		this.municipalWard = municipalWard;
	}
	public String getStreetBunch() {
		return streetBunch;
	}
	public void setStreetBunch(String streetBunch) {
		this.streetBunch = streetBunch;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public Integer getAncestralHome() {
		return ancestralHome;
	}
	public void setAncestralHome(Integer ancestralHome) {
		this.ancestralHome = ancestralHome;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtherEmail() {
		return otherEmail;
	}
	public void setOtherEmail(String otherEmail) {
		this.otherEmail = otherEmail;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Integer getSmoking() {
		return smoking;
	}
	public void setSmoking(Integer smoking) {
		this.smoking = smoking;
	}
	public Integer getSake() {
		return sake;
	}
	public void setSake(Integer sake) {
		this.sake = sake;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}
	public String getAssets() {
		return assets;
	}
	public void setAssets(String assets) {
		this.assets = assets;
	}
	public Integer getHoliday() {
		return holiday;
	}
	public void setHoliday(Integer holiday) {
		this.holiday = holiday;
	}
	public Integer getResidenceForm() {
		return residenceForm;
	}
	public void setResidenceForm(Integer residenceForm) {
		this.residenceForm = residenceForm;
	}
	public Integer getHomeRowing() {
		return homeRowing;
	}
	public void setHomeRowing(Integer homeRowing) {
		this.homeRowing = homeRowing;
	}
	public String getFamilyMembers() {
		return familyMembers;
	}
	public void setFamilyMembers(String familyMembers) {
		this.familyMembers = familyMembers;
	}
	public Integer getMarriageHistory() {
		return marriageHistory;
	}
	public void setMarriageHistory(Integer marriageHistory) {
		this.marriageHistory = marriageHistory;
	}
	public String getLivingTogether() {
		return livingTogether;
	}
	public void setLivingTogether(String livingTogether) {
		this.livingTogether = livingTogether;
	}
	public Integer getRemarriageNum() {
		return remarriageNum;
	}
	public void setRemarriageNum(Integer remarriageNum) {
		this.remarriageNum = remarriageNum;
	}
	public Integer getRemarriageSituation() {
		return remarriageSituation;
	}
	public void setRemarriageSituation(Integer remarriageSituation) {
		this.remarriageSituation = remarriageSituation;
	}
	public String getDivorceReason() {
		return divorceReason;
	}
	public void setDivorceReason(String divorceReason) {
		this.divorceReason = divorceReason;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getXzphotourl() {
		return xzphotourl;
	}
	public void setXzphotourl(String xzphotourl) {
		this.xzphotourl = xzphotourl;
	}
	
	
	
}
