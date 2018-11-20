package ycw.system.vo;

import ycw.system.pojo.base.TSDepart;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSLog;

public class DepartVo extends TSDepart{

	private String iconPath;
	private String parentId;
	private String parentdepartid;
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentdepartid() {
		return parentdepartid;
	}
	public void setParentdepartid(String parentdepartid) {
		this.parentdepartid = parentdepartid;
	}
	
	
	
	
}
