package ycw.om.form.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ycw.om.form.entity.MemberPointsLevelEntity;
import ycw.om.form.service.MemberPointsLevelService;

/**
 * @author 曹宇安
 *
 * @Description 会员等级controller
 */
@Controller
@RequestMapping("/memberpointslevel")
public class MemberPointsLevelController {
	
	@Autowired
	MemberPointsLevelService memberPointsLevelService;
	
	
	/**
	 * 查找会员等级
	 */
	@RequestMapping(params="selectAll")
	@ResponseBody
	public Map<String,Object> selectMemberPointsLevel(@RequestParam Map<String,Object> map){
		
		if(1 == 1){
			
			//查找所有
			List<MemberPointsLevelEntity> levelList = memberPointsLevelService.getLevelList(map);
			//把集合放回map里
			map.put("levelList", levelList);
		}
		if(!map.isEmpty()){
			return map;
		} else {
			Map<String,Object> map2 = new HashMap<String,Object>(); 
			map2.put("wzf", "wzf");
			
			return map2;
		}
		
		
	}
	
}
