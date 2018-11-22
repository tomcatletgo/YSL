package ycw.om.form.entity;


/**
 * @author 曹宇安
 *
 * @Description 会员实名认证信息
 */
public class MemberAuthenticationEntity extends BaseEntity{

	private String mamberId;
	private String realName;
	private String phone;
	private String IDNumber;
	private Integer auditStatus;
	
	private MemberEntity memberEntity;

	
	
	public String getMamberId() {
		return mamberId;
	}

	public void setMamberId(String mamberId) {
		this.mamberId = mamberId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public MemberEntity getMemberEntity() {
		return memberEntity;
	}

	public void setMemberEntity(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}
	
	
	
}
