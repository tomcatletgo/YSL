package ycw.system.pojo.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import core.common.entity.IdEntity;

/**
 * 系统用户父类表
 * @author
 */
@Entity
@Table(name = "t_s_base_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class TSBaseUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String realName;// 真实姓名
	private String idCard;// 身份证
	private String driverLicense;// 驾驶证
	private Timestamp contractSigning;// 合同签定时间
	private String contractValidity;// 合同有效期
	private String imgID;//图片存储ID
	//private Short isdelete;// 逻辑删除
	private TSDepart TSDepart = new TSDepart();// 部门
	private String depart;
	//private String browser;// 用户使用浏览器类型
	//private String userKey;// 用户验证唯一标示
	//private Short activitiSync;//是否同步工作流引擎
	//private TBAtdcCfg atdcCfg = new TBAtdcCfg();
	//private byte[] signature;// 签名文件
	
//	@JsonIgnore    //getList查询转换为列表时处理json转换异常
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "atdcCfgid")
//	public TBAtdcCfg getAtdcCfg() {
//		return atdcCfg;
//	}
//	public void setAtdcCfg(TBAtdcCfg atdcCfg) {
//		this.atdcCfg = atdcCfg;
//	}
//	@Column(name = "signature",length=3000)
//	public byte[] getSignature() {
//		return signature;
//	}
//	public void setSignature(byte[] signature) {
//		this.signature = signature;
//	}
//
//	@Column(name = "browser", length = 20)
//	public String getBrowser() {
//		return browser;
//	}
//
//	public void setBrowser(String browser) {
//		this.browser = browser;
//	}
//
//	@Column(name = "userkey", length = 200)
//	public String getUserKey() {
//		return userKey;
//	}
//
//	public void setUserKey(String userKey) {
//		this.userKey = userKey;
//	}


//	public Short getActivitiSync() {
//		return activitiSync;
//	}
//	@Column(name = "activitisync")
//	public void setActivitiSync(Short activitiSync) {
//		this.activitiSync = activitiSync;
//	}
	
	@JsonIgnore    //getList查询转换为列表时处理json转换异常
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departid")
	public TSDepart getTSDepart() {
		return this.TSDepart;
	}

	public void setTSDepart(TSDepart TSDepart) {
		this.TSDepart = TSDepart;
	}

	@Column(name = "realName", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
//	@Column(name = "isdelete")
//	public Short getIsdelete() {
//		return isdelete;
//	}
//	public void setIsdelete(Short isdelete) {
//		this.isdelete = isdelete;
//	}

	@Column(name = "ID_CARD")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "DRIVER_LICENSE")
	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	@Column(name = "CONTRACT_SIGNING")
	public Timestamp getContractSigning() {
		return contractSigning;
	}

	public void setContractSigning(Timestamp contractSigning) {
		this.contractSigning = contractSigning;
	}

	@Column(name = "CONTRACT_VALIDITY")
	public String getContractValidity() {
		return contractValidity;
	}

	public void setContractValidity(String contractValidity) {
		this.contractValidity = contractValidity;
	}

	@Column(name = "IMG_ID")
	public String getImgID() {
		return imgID;
	}

	public void setImgID(String imgID) {
		this.imgID = imgID;
	}

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
	

}