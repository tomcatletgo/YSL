package ycw.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ycw.system.pojo.base.TSBaseUser;

/**
 * 系统用户表
 *  @author
 */
@Entity
@Table(name = "t_s_user")
@PrimaryKeyJoinColumn(name = "id")
public class TSUser extends TSBaseUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userName;// 用户名
	private String password;//用户密码
	private Short status;// 状态1停用：,0：启用
	private Short isdelete;// 逻辑删除 1.未删除  0.已删除
	private String businessesRowId; // 公司ID 
	
	@Column(name = "username", length = 100)
	public String getUserName() {
		return this.userName;
	}
	 
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "businessesRowId", length = 100)
	public String getBusinessesRowId() {
		return businessesRowId;
	}
	public void setBusinessesRowId(String businessesRowId) {
		this.businessesRowId = businessesRowId;
	}
	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "isdelete")
	public Short getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(Short isdelete) {
		this.isdelete = isdelete;
	}
	
//	private String signatureFile;// 签名文件
//	private String mobilePhone;// 手机
//	private String officePhone;// 办公电话
//	private String isLocation;// 是否定位
//	private String userNum;
//	private String email;// 邮箱
//	@Column(name = "signatureFile", length = 100)
//	public String getSignatureFile() {
//		return this.signatureFile;
//	}
//
//	public void setSignatureFile(String signatureFile) {
//		this.signatureFile = signatureFile;
//	}
//
//	@Column(name = "mobilePhone", length = 30)
//	public String getMobilePhone() {
//		return this.mobilePhone;
//	}
//
//	public void setMobilePhone(String mobilePhone) {
//		this.mobilePhone = mobilePhone;
//	}
//
//	@Column(name = "officePhone", length = 20)
//	public String getOfficePhone() {
//		return this.officePhone;
//	}
//
//	public void setOfficePhone(String officePhone) {
//		this.officePhone = officePhone;
//	}
//
//	@Column(name = "email", length = 50)
//	public String getEmail() {
//		return this.email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	
//	@Column(name = "islocation", length = 10)
//	public String getIsLocation() {
//		return isLocation;
//	}
//
//	public void setIsLocation(String isLocation) {
//		this.isLocation = isLocation;
//	}

//	@Column(name = "userNum", length = 48)
//	public String getUserNum() {
//		return userNum;
//	}
//
//	public void setUserNum(String userNum) {
//		this.userNum = userNum;
//	}
}