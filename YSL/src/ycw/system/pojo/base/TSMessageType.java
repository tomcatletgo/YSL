package ycw.system.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import core.common.entity.IdEntity;

@Entity
@Table(name = "t_s_message_type")
public class TSMessageType extends IdEntity implements java.io.Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3873962121470490856L;
    private String typeCode;    // 类型编码
    private String typeName;    // 类型名称
    private String orderNum;    // 排序号
    
    @Column(name = "typeCode", length = 32)
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "typeName", length = 32)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "orderNum", length = 32)
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
