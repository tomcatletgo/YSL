package ycw.om.form.common;

/**
 * 订单状态
 * 
 * @author joe
 *
 *         2017年11月8日
 */
public enum NSOrderStatusEnum {
	
	ORDER_APPEAL_5("买方申述－待发货阶段", 71),
	ORDER_APPEAL_6("卖方申述－待发货阶段", 72),
	ORDER_APPEAL_7("买方申述－待收货阶段", 73),
	ORDER_APPEAL_8("卖方申述－待收货阶段", 74),
	ORDER_WITHDRAW_81("买方提现处理完成 ", 81),
	ORDER_WITHDRAW_82("卖方提现处理完成 ", 82),
	ORDER_WITHDRAW_83("买方退款处理完成 ", 83),
	 
    SERVICE_FINISH("处理完成 ", 90);
	 


    // 成员变量
    private String name;
    private int	   index;

    // 构造方法
    private NSOrderStatusEnum(String name, int index) {
	this.name = name;
	this.index = index;
    }

    /**
     * 根本code取出枚举名称
     * 
     * @param index
     * @return
     */
    public static String getName(int index) {
	for (NSOrderStatusEnum c : NSOrderStatusEnum.values()) {
	    if (c.getIndex() == index) {
		return c.name;
	    }
	}
	return null;
    }

    /**
     * 根据枚举表取得对应code
     * 
     * @param name
     * @return
     */
    public static int getIndex(String name) {
	for (NSOrderStatusEnum c : NSOrderStatusEnum.values()) {
	    if (c.getName().equals(name)) {
		return c.getIndex();
	    }
	}
	return -1;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getIndex() {
	return index;
    }

    public String getIndexStr() {
	return String.valueOf(index);
    }

    public void setIndex(int index) {
	this.index = index;
    }

}
