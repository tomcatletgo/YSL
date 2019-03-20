package com.marriage.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import org.apache.ibatis.javassist.compiler.ast.NewExpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.sql.visitor.functions.If;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.marriage.uitl.PhotoUtil;
import com.marriage.user.entity.ManInformation;
import com.marriage.user.entity.User;
import com.marriage.user.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	
	@RequestMapping("/gg")
	@ResponseBody
	public String gg(){
		
		return "gg";
	}
	
	/**
	 * 添加用户 返回0或1
	 * @不会添加管理员账户
	 */
	@RequestMapping(value="/adduser",method=RequestMethod.GET)
	@ResponseBody
	public Integer AddUser(@RequestParam Map<String,Object> map){
		Integer character = null;
		if(map.get("userCharacter")!=null){
			character = Integer.parseInt(map.get("userCharacter").toString());
		}
		//TODO 图片上传
		
		//url放进map.imgUrl TODO  测试
		
		
		if(character == 1){
			//调用添加男性方法
			return userService.addMan(map);
		} else if (character == 2){
			//调用添加女性方法  ..?
			//弃疗  扔进information那边  因为没账号
		}
		
		return 0;
	}
	
	
	/**
	 * 查寻男性信息  的第几页
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody 
	public Map<String,Object> selectManInformationByPage(@RequestParam Map<String,Object> map){
		Map<String, Object> replyMap = new HashMap<String,Object>();
		
		//页数
		if(map.get("page") != null && map.get("size") != null){
			//算页
			int page = Integer.parseInt(map.get("page").toString());
			int size = Integer.parseInt(map.get("size").toString());
			if (page <= 0 || size <= 0) {
				replyMap.put("message", "page error");
			}
			int start = size*(page-1);
			map.put("start", start);
			map.put("page", page);
			map.put("size", size);
			
			//接收的list
			List<ManInformation> manList = null;
			manList = userService.selectManInformationBypage(map);
			//放进map去
			replyMap.put("manList", manList);
			
		} else {
			replyMap.put("message", "page error");
		}
		
		return replyMap;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Integer updateManInformationByUserId(@RequestParam Map<String,Object> map){
		
		if (map.get("userId") != null && map.size() > 1) {
			
			return userService.updateManInformationByUserId(map);
		}
		
		return 0;
	}
	
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public Integer deleteUserById(@RequestParam Map<String,Object> map){
		
		//大概需要取个数组 放 map.ids
		
		if (map.get("userId") != null || map.get("ids") != null) {
			
			//id同样当做数组放进ids里
			if (map.get("userId") != null) {
				
				String id = map.get("userId").toString();
				map.put("ids", id);
			}
			
			
			return userService.deleteUserById(map);
		}
		
		return 0;
	}
	
	
	//查看账号是否存在 0 not used  1 is used  -1 no userName  -5 error(不可能就是..)
	@RequestMapping(value="/checkusername")
	@ResponseBody
	public Integer checkUserNameIsUsedOrNot(@RequestParam("userName") String userName){
		
		//System.out.println(userName+"########################################################################");
		
		if (null != userName) {
			
			return userService.checkUserNameIsUsedOrNot(userName);
		}
		
		
		return -1;
	}
	
	/**
	 * auod wangdiran  前台 登录  接口     本人已测试    发送参数正常   
	 * 
	 * userName  password    所需参数
	 * 
	 *  用户状态必须是已激活状态才能够登录，不然返回值都是500
	 * 
	 * **/
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)//TODO  post
	@ResponseBody
	public Map<String, Object>  userLogin(@RequestParam Map<String,Object> map,HttpServletRequest request){
	    List<User>  user = new ArrayList<>();
		Map<String, Object> replyMap = new HashMap<String,Object>();
		if (map.containsKey("userName") && map.containsKey("password")) {
			user = userService.userLogin(map);
		}
		if(user.size() > 0 ){
			replyMap.put("code", "200");
			replyMap.put("state", "success");
			replyMap.put("userinfo", user);
		}else{
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
		return replyMap;
	}
	
	/**
	 * auod wangdiran  前台 注册  接口     图片测试没有测试     其余都已测试
	 * **/
	@RequestMapping(value="/useradds",method=RequestMethod.POST)//TODO  post
	@ResponseBody
	public Map<String, Object>  userAdds(@RequestParam("xzphotourl") MultipartFile file,@RequestParam Map<String,Object> map,HttpServletRequest request){
	    int  user = 0 ;
	    int  man = 0 ;
		Map<String, Object> replyMap = new HashMap<String,Object>();
		if (    map.containsKey("userName") && map.containsKey("password")&&
				map.containsKey("true_name") && map.containsKey("karigana")&&
				map.containsKey("romaji") && map.containsKey("postal_code")&&
				map.containsKey("prefectures") && map.containsKey("municipal_ward")&&
				map.containsKey("street_bunch") && map.containsKey("building_name")&&
				map.containsKey("ancestral_home") && map.containsKey("phone")&&
				map.containsKey("mobile_phone") && map.containsKey("email")&&
				map.containsKey("other_email") && map.containsKey("line_id")&&
				map.containsKey("date_of_birth") && map.containsKey("age")&&
				map.containsKey("hobby") && map.containsKey("qualification")&&
				map.containsKey("smoking") && map.containsKey("sake")&&
				map.containsKey("height") && map.containsKey("weight") && 
				map.containsKey("blood_group")&&map.containsKey("education")&& 
				map.containsKey("blood_group")&&map.containsKey("occupation")&& 
				map.containsKey("annual_income")&&map.containsKey("assets")&& 
				map.containsKey("holiday")&&map.containsKey("residence_form") && 
				map.containsKey("home_rowing")&&map.containsKey("family_members")&& 
				map.containsKey("marriage_history")&&map.containsKey("living_together")&& 
				map.containsKey("remarriage_num")&&map.containsKey("remarriage_situation")&& 
			    map.containsKey("divorce_reason")&&map.containsKey("message")&& 
				map.containsKey("remark")&&map.containsKey("xzphotourl")
				) {
				
				
		}
		//添加到user表里   默认状态为 未激活   ---- star
		user = userService.userAddsUser(map);
		//-----------End
		
		//根据用用户名，密码来查询userid    ---star
	    User  us = new User();
		us = userService.userUserId(map);
		System.out.println("user_id======="+us.getUser_id());
		map.put("userId",us.getUser_id());
		//-----------End
		
		//添加到man_information表中
		System.err.println("xzphotourl==="+map.get("xzphotourl"));
		replyMap.put("xzphotourl",PhotoUtil.saveFile(file,request));
		System.err.println("xzphotourl======="+replyMap.get("xzphotourl"));
		man = userService.userAdds(map);
		
		if(user > 0 && man >0){
			replyMap.put("code", "200");
			replyMap.put("state", "success");
		}else{
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
		return replyMap;
	}
	
	
	
	//图片上传   非base64加密模式上传图片
	@RequestMapping(value = "upload",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> upload(
     @RequestParam("file") MultipartFile file , HttpServletRequest request){
		 Map<String, Object> replyMap = new HashMap<String,Object>();
       try {
           request.setAttribute("img",PhotoUtil.saveFile(file,request));
          replyMap.put("url",PhotoUtil.saveFile(file,request));
          replyMap.put("code", "200");
          replyMap.put("state", "success");
		} catch (Exception e) {
			replyMap.put("code", "500");
			replyMap.put("state", "error");
		}
		return replyMap;
		
    }
	
	
}
