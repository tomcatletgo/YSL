package com.marriage.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marriage.img.service.ImgService;
import com.marriage.user.dao.UserDao;
import com.marriage.user.entity.ManInformation;
import com.marriage.user.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ImgService imgService;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer addMan(Map<String, Object> map) {

		//添加账号 返回id进map
		int num = userDao.addUser(map);
		
		//测试用添加map值    会删
		//putMapInformationsss(map);
		
		//可以空的信息若是空 添加"无"
		addNullToNone(map);
		
		//应该还有个添加图片。。。那么图片是怎么传过来的md.....   TODO
		
		
		//添加information 
		userDao.addManInformation(map);
		//System.out.println(map.get("userId").toString()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$hosjidsgga'ngwe2034rikpwqf, p023u t");
		//System.out.println(num); ok
		
		//如果有图片添加图片url    TODO  测试
		if (map.containsKey("imgUrl") && map.get("imgUrl") != null) {
			imgService.addUserImg(map);
		}
		
		return num;
	}

	
	
	
	/**
	 * 
	 * @param map
	 * @return
	 *  如果有可空项没填   填上  (tmd为什么不在前端做这事)
	 */
	public Integer addNullToNone(Map<String, Object> map){
		int count = 0;
		
		if (!map.containsKey("romaji") || map.get("romaji") == null) {
			//不这个不可能空...
			count++;
			map.put("romaji", "なし");
		}
		if (!map.containsKey("phone") || map.get("phone") == null) {
			count++;
			map.put("phone", "なし");
		} 
		if (!map.containsKey("otherEmail") || map.get("otherEmail") == null) {
			count++;
			map.put("otherEmail", "なし");
		} 
		if (!map.containsKey("lineId") || map.get("lineId") == null) {
			count++;
			map.put("lineId", "なし");
		} 
		if (!map.containsKey("qualification") || map.get("qualification") == null) {
			count++;
			map.put("qualification", "なし");
		} 
		if (!map.containsKey("remarriageNum") || map.get("remarriageNum") == null) {
			count++;
			map.put("remarriageNum", "なし");
		} 
		if (!map.containsKey("remarriageSituation") || map.get("remarriageSituation") == null) {
			count++;
			map.put("remarriageSituation", "なし");
		} 
		if (!map.containsKey("divorceReason") || map.get("divorceReason") == null) {
			count++;
			map.put("divorceReason", "なし");
		} 
		if (!map.containsKey("message") || map.get("message") == null) {
			count++;
			map.put("message", "なし");
		} 
		if (!map.containsKey("remark") || map.get("remark") == null) {
			count++;
			map.put("remark", "なし");
		}
		
		return count;
	}
	
	
	
	/**
	 * 手动添加信息ing
	 */
	private Integer putMapInformationsss(Map<String, Object> map){
		
		map.put("trueName", "佐仓绫音");
		map.put("karigana", "さくらあやね");
		map.put("romaji", "Sakura Ayane");
		map.put("postalCode", "123-1235");
		map.put("prefectures", "1");
		map.put("municipalWard", "千歳市");
		map.put("streetBunch", "哔哩哔哩");
		map.put("buildingName", "3-3？");
		map.put("ancestralHome", "1");
		map.put("phone", "88888888");
		map.put("mobilePhone", "13579797979");
		map.put("email", "pooi@poi.com");
		map.put("otherEmail", "kamo@kamo.com");
		//此处应有个line id    testing
		map.put("dateOfBirth", "1994-01-29");
		map.put("age", "25");
		map.put("hobby", "橘,(被)登山");
		map.put("qualification", "第12届声优奖中获得助演女优赏及主持赏");
		map.put("smoking", "0");
		map.put("sake", "0");
		map.put("height", "157");
		map.put("weight", "");
		map.put("bloodGroup", "B");
		map.put("education", "本");
		map.put("occupation", "CV");
		map.put("annualIncome", "先算她一个亿");
		map.put("assets", "那就算他两个亿");
		map.put("holiday", "2");
		map.put("residenceForm", "1");
		map.put("homeRowing", "1");
		map.put("familyMembers", "父 母");
		map.put("marriageHistory", "0");
		map.put("livingTogether", "是");
		map.put("remarriageNum", "11");
		map.put("remarriageSituation", "1");
		map.put("divorceReason", "因为换了下一个节目(..)");
		map.put("message", "性别不限");
		map.put("remark", "备注无");
		
		
		return 0;
	}




	@Override
	public List<ManInformation> selectManInformationBypage(Map<String, Object> map) {
		
		return userDao.selectManInformationByPage(map);
	}




	@Override
	public Integer updateManInformationByUserId(Map<String, Object> map) {
		
		
		return userDao.updateManInformationByUserId(map);
	}
}
