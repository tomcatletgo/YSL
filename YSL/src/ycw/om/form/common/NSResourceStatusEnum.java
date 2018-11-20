package ycw.om.form.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源状态枚举
 * 
 * @author zhangqichao
 * 
 */
public enum NSResourceStatusEnum {

	// 资源类型（0：图片 1：文件 2：收藏 3：其它 4:二维码 5:待接受 6:待收货 7:订单取消 8:待发货 9:首页频道(注* 频道名字:图片url
	// 【图片与url之间用半角 : 分隔】) ）10:保证金率,11:公司名称 12:公司支付宝账号信息 13:仓库分类 14:订单状态

	IMAGE("图片", 0), FILE("文件", 1), FAVOR("收藏", 2), OTHER("其它", 3), QR("二维码", 4), ORDERED("待接受", 5), ORDER_FINISH("待收货",
			6), ORDER_CANCEL("订单取消", 7), ORDER_PAID("待发货", 8), CHANNEL("首页频道", 9);


	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private NSResourceStatusEnum(String name, int index) {
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
		for (NSResourceStatusEnum c : NSResourceStatusEnum.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}
	public static List<String> getNames() {
		List<String> rList = new ArrayList<String>();
		for (NSResourceStatusEnum c : NSResourceStatusEnum.values()) {
			rList.add(c.name);
			
		}
		return rList;
	}

	/**
	 * 根据枚举表取得对应code
	 * 
	 * @param name
	 * @return
	 */
	public static int getIndex(String name) {
		for (NSResourceStatusEnum c : NSResourceStatusEnum.values()) {
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
