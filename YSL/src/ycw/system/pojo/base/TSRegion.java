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
@Table(name = "t_s_region")
public class TSRegion extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private TSRegion TSPRegion;//父地域
	private String regionName;//地域名称
	private Short regionLevel;//等级
	private String regionSort;//同区域中的显示顺序
	private String regionCode;//区域码
	private List<TSRegion> TSRegions = new ArrayList<TSRegion>();
	private String isDelete;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentregionid")
	public TSRegion getTSPRegion() {
		return this.TSPRegion;
	}
	public void setTSPRegion(TSRegion TSPRegion) {
		this.TSPRegion = TSPRegion;
	}
	@Column(name = "regionName", length = 50)
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Column(name = "regionLevel",length = 1)
	public Short getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(Short regionLevel) {
		this.regionLevel = regionLevel;
	}
	@Column(name = "regionSort",length = 3)
	public String getRegionSort() {
		return regionSort;
	}
	public void setRegionSort(String regionSort) {
		this.regionSort = regionSort;
	}
	@Column(name = "regionCode",length = 10)
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPRegion")
	public List<TSRegion> getTSRegions() {
		return this.TSRegions;
	}
	public void setTSRegions(List<TSRegion> TSRegions) {
		this.TSRegions = TSRegions;
	}
	@Column(name = "isdelete",length = 5)
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}