package ycw.system.pojo.base;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import core.common.entity.IdEntity;

/**
 *地域管理表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_transformers")
public class TSTransformers extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private TSRegion TSRegion;//父地域
	private String transformersName;//地域名称
	private String transformersCode;//区域码
	private String isDelete;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regionid")
	public TSRegion getTSRegion() {
		return this.TSRegion;
	}
	public void setTSRegion(TSRegion TSRegion) {
		this.TSRegion = TSRegion;
	}
	@Column(name = "transformersName", length = 50)
	public String getTransformersName() {
		return transformersName;
	}
	public void setTransformersName(String transformersName) {
		this.transformersName = transformersName;
	}
	@Column(name = "transformersCode",length = 10)
	public String getTransformersCode() {
		return transformersCode;
	}
	public void setTransformersCode(String transformersCode) {
		this.transformersCode = transformersCode;
	}
	@Column(name = "isDelete",length = 5)
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}