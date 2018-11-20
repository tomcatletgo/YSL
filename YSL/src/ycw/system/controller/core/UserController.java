package ycw.system.controller.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import core.common.hibernate.qbc.CriteriaQuery;
import core.common.model.common.UploadFile;
import core.common.model.json.AjaxJson;
import core.common.model.json.ComboBox;
import core.common.model.json.DataGrid;
import core.common.model.json.ValidForm;
import core.constant.Globals;
import core.util.ListtoMenu;
import core.util.PasswordUtil;
import core.util.ResourceUtil;
import core.util.RoletoJson;
import core.util.SetListSort;
import core.util.StringUtil;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import ycw.system.pojo.base.TSBaseUser;
import ycw.system.pojo.base.TSDepart;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.pojo.base.TSUser;
import ycw.system.service.DepartService;
import ycw.system.service.SystemService;
import ycw.system.service.UserService;
import ycw.system.vo.UserVo;

/**
 * @ClassName: UserController
 * @Description: 用户管理处理类
 * @author 张代浩
 */
@Controller
@RequestMapping("/userController")
public class UserController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserController.class);

	private UserService userService;
	private SystemService systemService;
	private String message = null;

	@Autowired
	private DepartService departService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 菜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "menu")
	public void menu(HttpServletRequest request, HttpServletResponse response) {
		SetListSort sort = new SetListSort();
		TSUser u = ResourceUtil.getSessionUserName();
		// 登陆者的权限
		Set<TSFunction> loginActionlist = new HashSet<TSFunction>();// 已有权限菜单
		List<TSRoleUser> rUsers = systemService.findByProperty(
				TSRoleUser.class, "TSUser.id", u.getId());
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			List<TSRoleFunction> roleFunctionList = systemService
					.findByProperty(TSRoleFunction.class, "TSRole.id",
							role.getId());
			if (roleFunctionList.size() > 0) {
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = (TSFunction) roleFunction
							.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		List<TSFunction> bigActionlist = new ArrayList<TSFunction>();// 一级权限菜单
		List<TSFunction> smailActionlist = new ArrayList<TSFunction>();// 二级权限菜单
		if (loginActionlist.size() > 0) {
			for (TSFunction function : loginActionlist) {
				if (function.getFunctionLevel() == 0) {
					bigActionlist.add(function);
				} else if (function.getFunctionLevel() == 1) {
					smailActionlist.add(function);
				}
			}
		}
		// 菜单栏排序
		Collections.sort(bigActionlist, sort);
		Collections.sort(smailActionlist, sort);
		String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
		// request.setAttribute("loginMenu",logString);
		try {
			response.getWriter().write(logString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户列表页面跳转[跳转到标签和手工结合的html页面]
	 * 
	 * @return
	 */
	@RequestMapping(params = "userDemo")
	public String userDemo(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		request.setAttribute("departsReplace",
				RoletoJson.listToReplaceStr(departList, "departname", "id"));
		return "system/user/userList2";
	}

	/**
	 * 考勤列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "atdcCfgs")
	public String atdcCfgs(HttpServletRequest request) {
		return "system/user/atdcCfgs";
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(params = "user")
	public String user(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		request.setAttribute("departsReplace",
				RoletoJson.listToReplaceStr(departList, "departname", "id"));
		return "system/user/userList";
	}

	/**
	 * 用户信息
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(params = "userinfo")
	public String userinfo(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		return "system/user/userinfo";
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(params = "changepassword")
	public String changepassword(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		return "system/user/changepassword";
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "savenewpwd")
	@ResponseBody
	public AjaxJson savenewpwd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		String password = oConvertUtils.getString(request
				.getParameter("password"));
		String newpassword = oConvertUtils.getString(request
				.getParameter("newpassword"));
		String pString = PasswordUtil.encrypt(user.getUserName(), password,
				PasswordUtil.getStaticSalt());
		if (!pString.equals(user.getPassword())) {
			j.setMsg("原密码不正确");
			j.setSuccess(false);
		} else {
			try {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(),
						newpassword, PasswordUtil.getStaticSalt()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemService.updateEntitie(user);
			// j.setMsg("修改成功");

		}
		return j;
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "initpwd")
	@ResponseBody
	public AjaxJson initpwd(HttpServletRequest request, TSUser user) {
		AjaxJson j = new AjaxJson();
		user = this.userService.get(TSUser.class, user.getId());
		user.setPassword(PasswordUtil.encrypt(user.getUserName(), "123456",
				PasswordUtil.getStaticSalt()));
		userService.updateUserPassword(user);
		message = "用户: " + user.getUserName() + "初始化密码成功";
		systemService.addLog(message, Globals.Log_Type_UPDATE,
				Globals.Log_Leavel_INFO);
		// j.setMsg(message);
		return j;
	}

	/**
	 * 得到角色列表
	 * 
	 * @param response
	 * @param request
	 * @param comboBox
	 * @return List<ComboBox>
	 */
	@RequestMapping(params = "role")
	@ResponseBody
	public List<ComboBox> role(HttpServletResponse response,
			HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSRole> roles = new ArrayList<TSRole>();
		if (StringUtil.isNotEmpty(id)) {
			List<TSRoleUser> roleUser = systemService.findByProperty(
					TSRoleUser.class, "TSUser.id", id);
			if (roleUser.size() > 0) {
				for (TSRoleUser ru : roleUser) {
					roles.add(ru.getTSRole());
				}
			}
		}
		List<TSRole> roleList = systemService.getList(TSRole.class);
		comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);
		return comboBoxs;
	}

	/**
	 * 得到部门列表
	 * 
	 * @param response
	 * @param request
	 * @param comboBox
	 * @return List<ComboBox>
	 */
	@RequestMapping(params = "depart")
	@ResponseBody
	public List<ComboBox> depart(HttpServletResponse response,
			HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSDepart> departs = new ArrayList<TSDepart>();
		if (StringUtil.isNotEmpty(id)) {
			TSUser user = systemService.get(TSUser.class, id);
			if (user.getTSDepart() != null) {
				TSDepart depart = systemService.get(TSDepart.class, user
						.getTSDepart().getId());
				departs.add(depart);
			}
		}
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
		return comboBoxs;
	}

	/**
	 * easyuiAJAX用户列表请求数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TSUser user, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		// 获取前台参数
		/*String departId = user.getTSDepart().getId();
		String userName = user.getUserName(); */
		String userName = request.getParameter("userName"); 
		String businessesName = request.getParameter("businessesName"); 
		Map<String, Object> map = new HashMap<String, Object>();
		/*if (!StringUtil.isEmpty(departId)) {
			Map<String, Object> departMap = new HashMap<String, Object>();
			departMap.put("departid", departId);
			TSDepart depart = this.systemService.get(TSDepart.class, departId);
//			TSDepart depart = departService.getDepart(departMap);
			if (depart != null) {
				List<String> departidList = new ArrayList<String>();
				departidList = getDepartidList(departidList, depart);
				map.put("departId", departidList);
			}
		}*/
//		map.put("departId", departId);
		map.put("businessesName", businessesName); 
		map.put("userName", userName); 
		int rows = dataGrid.getRows();
		int page = dataGrid.getPage();
		map.put("begin", page * rows - rows);
		map.put("end", rows);

		List results = userService.getUser(map);

		int total = userService.getUserTotle(map);
		dataGrid.setTotal(total);
		dataGrid.setResults(results);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 得到部门列表含下级
	 * 
	 * @param departidList
	 * @param depart
	 * @return List<String>
	 */
	public List<String> getDepartidList(List<String> departidList,
			TSDepart depart) {
		departidList.add(depart.getId());
		if (depart.getTSDeparts() != null) {
			for (int i = 0; i < depart.getTSDeparts().size(); i++) {
				TSDepart dpt = depart.getTSDeparts().get(i);
				departidList = getDepartidList(departidList, dpt);
			}
		}
		return departidList;
	}

	/**
	 * 删除
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSUser user, HttpServletRequest req, TSRoleUser tsRoleUser) {
		AjaxJson j = new AjaxJson();
		if ("admin".equals(user.getUserName())) {
			message = "超级管理员[admin]不可删除";
			j.setMsg(message);
			return j;
		}
		if (!Globals.User_ADMIN.equals(user.getStatus())) {
			tsRoleUser.setTSUserID(user.getId());
			Integer vInteger = userService.deleteRoleUser(tsRoleUser);
			// 删除用户时先删除用户和角色关系表
			user.setIsdelete((short) 1);
			Integer terInteger = userService.updateUser(user);
			message = "用户: " + user.getUserName() + "删除成功";
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} else {
			message = "超级管理员不可删除";
			j.setMsg(message);
		}

		return j;
	}

	/**
	 * 同步删除用户角色关联表
	 * 
	 * @param user
	 * @return
	 */
	public void delRoleUser(TSUser user) {
		List<TSRoleUser> roleUserList = systemService.findByProperty(
				TSRoleUser.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (TSRoleUser tRoleUser : roleUserList) {
				systemService.delete(tRoleUser);
			}
		}
	}

	/**
	 * 检查用户名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkUser")
	@ResponseBody
	public ValidForm checkUser(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String userName = oConvertUtils
				.getString(request.getParameter("param"));
		String code = oConvertUtils.getString(request.getParameter("code"));
		List<TSUser> roles = systemService.findByProperty(TSUser.class,
				"userName", userName);
		if (roles.size() > 0 && !code.equals(userName)) {
			v.setInfo("用户名已存在");
			v.setStatus("n");
		}
		return v;
	}

	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "saveUser")
	@ResponseBody
	public AjaxJson saveUser(HttpServletRequest req, TSUser user,TSBaseUser baseuser,TSRoleUser roleuser) {
		AjaxJson j = new AjaxJson();
		// 得到用户的角色
		String roleid = oConvertUtils.getString(req.getParameter("roleid"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(user.getId())) {
			String departid = req.getParameter("TSDepart.id");
			baseuser.setDepart(departid);

			int b = userService.updateBaseuser(baseuser);
			if (b > 0) {
				message = "用户: " + user.getUserName() + "更新成功";
			}
			if (StringUtil.isNotEmpty(roleid)) {
				//用户角色表修改
				
					roleuser.setId(UUID.randomUUID().toString().replaceAll("-",""));
					String[] roleidIds = null;
					if (StringUtil.isNotEmpty(roleid)) {
						roleidIds = roleid.split(",");
					}
					roleuser.setTSUserID(user.getId());
					Integer vInteger = userService.deleteRoleUser(roleuser);
					for (String roleidId : roleidIds) {
						roleuser.setId(UUID.randomUUID().toString().replaceAll("-",""));
						roleuser.setTSUserID(user.getId());
						roleuser.setTSRoleID(roleidId);
						Integer vechicle = userService.addRoleUserInf(roleuser);
					}
					
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isUserName4Check", user.getUserName());
			map.put("begin", 0);
			map.put("end", 10);
			
			List results = userService.getUser(map);
		    if (results == null) {
                message = "用户: " + user.getUserName() + "已经存在";
            } else{ 
			    String ID = UUID.randomUUID().toString().replaceAll("-", "");
	            String userName = req.getParameter("userName");
	            String realName = req.getParameter("realName");
	            String passWord = req.getParameter("password");
	            String departid = req.getParameter("TSDepart.id");
	           
	            baseuser.setId(ID);
	            baseuser.setDepart(departid);
	            baseuser.setRealName(realName);
	            user.setId(ID);
	            user.setUserName(userName);
	            user.setRealName(realName);
	            user.setPassword(PasswordUtil.encrypt(user.getUserName(), passWord,
	    				PasswordUtil.getStaticSalt()));

	            
	            user.setStatus((short) 1);
	            user.setIsdelete((short)0);
	            int  b =  userService.addBaseUserInf(baseuser);
	            int  a =  userService.addUserInf(user);
				message = "用户: " + user.getUserName() + "添加成功";
					String[] roleidIdss = null;
					if (StringUtil.isNotEmpty(roleid)) {
						roleidIdss = roleid.split(",");
					}
					roleuser.setTSUserID(user.getId());
					Integer vInteger = userService.deleteRoleUser(roleuser);
					for (String roleidId : roleidIdss) {
						roleuser.setId(UUID.randomUUID().toString().replaceAll("-",""));
						roleuser.setTSUserID(user.getId());
						roleuser.setTSRoleID(roleidId);
						Integer vechicle = userService.addRoleUserInf(roleuser);
					}
				}
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
        
		}
        return j;
		}

	/*	return j;
	}*/

	/**
	 * 用户启用
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "enable")
	@ResponseBody
	public AjaxJson enable(HttpServletRequest req, TSUser user) {
		AjaxJson j = new AjaxJson();
		// 得到用户
		if (StringUtil.isNotEmpty(user.getId())) {
			user.setStatus(Globals.User_Normal);
			Integer terInteger = userService.updateUserStatus(user);
			if (terInteger > 0) {
				message = "用户: " + user.getUserName() + "启用成功";
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			}
			
		}
		return j;
	}

	/**
	 * 用户停用
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "disable")
	@ResponseBody
	public AjaxJson disable(HttpServletRequest req, TSUser user) {
		AjaxJson j = new AjaxJson();
		// 得到用户
		if (StringUtil.isNotEmpty(user.getId())) {
			user.setStatus(Globals.User_Forbidden);
			Integer terInteger = userService.updateUserStatus(user);
			if (terInteger > 0) {
				message = "用户: " + user.getUserName() + "停用成功";
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			}
			
		}
		return j;
	}

//	protected void saveRoleUser(TSUser user, String roleidstr) {
//		String[] roleids = roleidstr.split(",");
//
//		List<Group> groups = identityService.createGroupQuery().list();
//		for (Group group : groups) {
//			identityService.deleteMembership(user.getId(), group.getId());
//		}
//
//		for (int i = 0; i < roleids.length; i++) {
//			TSRoleUser rUser = new TSRoleUser();
//			TSRole role = systemService.getEntity(TSRole.class, roleids[i]);
//			rUser.setTSRole(role);
//			rUser.setTSUser(user);
//			systemService.save(rUser);
//			try {
//				identityService.createMembership(user.getId(), roleids[i]);
//			} catch (Exception e) {
//			}
//		}
//	}

	/**
	 * 用户选择角色跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "roles")
	public String roles() {
		return "system/user/users";
	}

	/**
	 * 用户选择部门跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "departs")
	public String departs() {
		return "system/user/departs";
	}

	/**
	 * 角色显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridRole")
	public void datagridRole(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX请求数据： 用户选择角色列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSUser user, HttpServletRequest req) {
		List<TSDepart> departList = new ArrayList<TSDepart>();
		String departid = oConvertUtils.getString(req.getParameter("departid"));
		if (!StringUtil.isEmpty(departid)) {
			departList.add((TSDepart) systemService.getEntity(TSDepart.class,
					departid));
		} else {
			departList.addAll((List) systemService.getList(TSDepart.class));
		}
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(user.getId())) {
			String id = user.getId();
			UserVo uservo = userService.getUserById(id);
//			user = systemService.getEntity(TSUser.class, user.getId());
			req.setAttribute("user", uservo);
			idandname(req, user);
			List<String> rolesUserList = userService.getrolesUserList(id);
			if (rolesUserList.size() > 0) {
				String str = "";
				for (String s : rolesUserList) {
					str += s + ",";
				}
				str = str.substring(0, str.length() - 1);
				req.setAttribute("rolesUser", str);
			}
		}
		return new ModelAndView("system/user/user");

	}

	/**
	 * 用户选择角色列表
	 * 
	 * @param req
	 * @param user
	 */
	public void idandname(HttpServletRequest req, TSUser user) {
		List<TSRoleUser> roleUsers = systemService.findByProperty(
				TSRoleUser.class, "TSUser.id", user.getId());
		String roleId = "";
		String roleName = "";
		if (roleUsers.size() > 0) {
			for (TSRoleUser tRoleUser : roleUsers) {
				roleId += tRoleUser.getTSRole().getId() + ",";
				roleName += tRoleUser.getTSRole().getRoleName() + ",";
			}
		}
		req.setAttribute("id", roleId);
		req.setAttribute("roleName", roleName);

	}

	// /**
	// * 根据部门和角色选择用户跳转页面
	// */
	// @RequestMapping(params = "choose")
	// public String choose(HttpServletRequest request) {
	// List<TSRole> roles = systemService.loadAll(TSRole.class);
	// request.setAttribute("roleList", roles);
	// return "system/membership/checkuser";
	// }
	//
	// /**
	// * 部门和角色选择用户的panel跳转页面
	// *
	// * @param request
	// * @return
	// */
	// @RequestMapping(params = "chooseUser")
	// public String chooseUser(HttpServletRequest request) {
	// String departid = request.getParameter("departid");
	// String roleid = request.getParameter("roleid");
	// request.setAttribute("roleid", roleid);
	// request.setAttribute("departid", departid);
	// return "system/membership/userlist";
	// }
	//
	// /**
	// * 部门和角色选择用户的用户显示列表
	// *
	// * @param request
	// * @param response
	// * @param dataGrid
	// */
	// @RequestMapping(params = "datagridUser")
	// public void datagridUser(HttpServletRequest request, HttpServletResponse
	// response, DataGrid dataGrid) {
	// String departid = request.getParameter("departid");
	// String roleid = request.getParameter("roleid");
	// CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
	// if (departid.length() > 0) {
	// cq.eq("TDepart.departid", oConvertUtils.getInt(departid, 0));
	// cq.add();
	// }
	// String userid = "";
	// if (roleid.length() > 0) {
	// List<TSRoleUser> roleUsers =
	// systemService.findByProperty(TSRoleUser.class, "TRole.roleid",
	// oConvertUtils.getInt(roleid, 0));
	// if (roleUsers.size() > 0) {
	// for (TSRoleUser tRoleUser : roleUsers) {
	// userid += tRoleUser.getTSUser().getId() + ",";
	// }
	// }
	// cq.in("userid", oConvertUtils.getInts(userid.split(",")));
	// cq.add();
	// }
	// this.systemService.getDataGridReturn(cq, true);
	// TagUtil.datagrid(response, dataGrid);
	// }
	//
	// /**
	// * 根据部门和角色选择用户跳转页面
	// */
	// @RequestMapping(params = "roleDepart")
	// public String roleDepart(HttpServletRequest request) {
	// List<TSRole> roles = systemService.loadAll(TSRole.class);
	// request.setAttribute("roleList", roles);
	// return "system/membership/roledepart";
	// }

	// /**
	// * 部门和角色选择用户的panel跳转页面
	// *
	// * @param request
	// * @return
	// */
	// @RequestMapping(params = "chooseDepart")
	// public ModelAndView chooseDepart(HttpServletRequest request) {
	// String nodeid = request.getParameter("nodeid");
	// ModelAndView modelAndView = null;
	// if (nodeid.equals("role")) {
	// modelAndView = new ModelAndView("system/membership/users");
	// } else {
	// modelAndView = new ModelAndView("system/membership/departList");
	// }
	// return modelAndView;
	// }
	//
	// /**
	// * 部门和角色选择用户的用户显示列表
	// *
	// * @param request
	// * @param response
	// * @param dataGrid
	// */
	// @RequestMapping(params = "datagridDepart")
	// public void datagridDepart(HttpServletRequest request,
	// HttpServletResponse response, DataGrid dataGrid) {
	// CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
	// systemService.getDataGridReturn(cq, true);
	// TagUtil.datagrid(response, dataGrid);
	// }
	//
	// /**
	// * 测试
	// *
	// * @param user
	// * @param req
	// * @return
	// */
	// @RequestMapping(params = "test")
	// public void test(HttpServletRequest request, HttpServletResponse
	// response) {
	// String jString = request.getParameter("_dt_json");
	// DataTables dataTables = new DataTables(request);
	// CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
	// String username = request.getParameter("userName");
	// if (username != null) {
	// cq.like("userName", username);
	// cq.add();
	// }
	// DataTableReturn dataTableReturn = systemService.getDataTableReturn(cq,
	// true);
	// TagUtil.datatable(response, dataTableReturn,
	// "id,userName,mobilePhone,TSDepart_departname");
	// }
	//
	// /**
	// * 用户列表页面跳转
	// *
	// * @return
	// */
	// @RequestMapping(params = "index")
	// public String index() {
	// return "bootstrap/main";
	// }
	//
	// /**
	// * 用户列表页面跳转
	// *
	// * @return
	// */
	// @RequestMapping(params = "main")
	// public String main() {
	// return "bootstrap/test";
	// }
	//
	// /**
	// * 测试
	// *
	// * @return
	// */
	// @RequestMapping(params = "testpage")
	// public String testpage(HttpServletRequest request) {
	// return "test/test";
	// }

	/**
	 * 设置签名跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addsign")
	public ModelAndView addsign(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView("system/user/usersign");
	}

	/**
	 * 用户录入
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "savesign", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savesign(HttpServletRequest req) {
		UploadFile uploadFile = new UploadFile(req);
		String id = uploadFile.get("id");
		TSUser user = systemService.getEntity(TSUser.class, id);
		uploadFile.setRealPath("signatureFile");
		uploadFile.setCusPath("signature");
		uploadFile.setByteField("signature");
		uploadFile.setBasePath("resources");
		uploadFile.setRename(false);
		uploadFile.setObject(user);
		AjaxJson j = new AjaxJson();
		message = user.getUserName() + "设置签名成功";
		systemService.uploadFile(uploadFile);
		systemService.addLog(message, Globals.Log_Type_INSERT,
				Globals.Log_Leavel_INFO);
		j.setMsg(message);

		return j;
	}

	// /**
	// * 测试组合查询功能
	// * @param user
	// * @param request
	// * @param response
	// * @param dataGrid
	// */
	// @RequestMapping(params = "testSearch")
	// public void testSearch(TSUser user, HttpServletRequest
	// request,HttpServletResponse response,DataGrid dataGrid) {
	// CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
	// if(user.getUserName()!=null){
	// cq.like("userName", user.getUserName());
	// }
	// if(user.getRealName()!=null){
	// cq.like("realName", user.getRealName());
	// }
	// cq.add();
	// this.systemService.getDataGridReturn(cq, true);
	// TagUtil.datagrid(response, dataGrid);
	// }

	/**
	 * @Title: saveStyle
	 * @Description: 修改首页样式
	 * @param request
	 * @return String
	 */
	@RequestMapping(params = "changestyle")
	public String changeStyle(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		if (user == null) {
			return "login/login";
		}
		String indexStyle = "blue";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
				continue;
			}
			if (cookie.getName().equalsIgnoreCase("INDEXSTYLE")) {
				indexStyle = cookie.getValue();
			}
		}
		request.setAttribute("indexStyle", indexStyle);
		return "system/user/changestyle";
	}

	/**
	 * @Title: saveStyle
	 * @Description: 保存首页样式
	 * @param request
	 * @param response
	 * @return AjaxJson
	 */
	@RequestMapping(params = "savestyle")
	@ResponseBody
	public AjaxJson saveStyle(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(Boolean.FALSE);
		TSUser user = ResourceUtil.getSessionUserName();
		if (user != null) {
			String indexStyle = request.getParameter("indexStyle");
			if (StringUtils.isNotEmpty(indexStyle)) {
				Cookie cookie = new Cookie("INDEXSTYLE", indexStyle);
				// 设置cookie有效期为一个月
				cookie.setMaxAge(3600 * 24 * 30);
				response.addCookie(cookie);
				j.setSuccess(Boolean.TRUE);
				j.setMsg("样式修改成功，请刷新页面");
			}
		} else {
			j.setMsg("请登录后再操作");
		}
		return j;
	}

	/**
	 * 检查用户名称唯一性
	 * 
	 * @param departName
	 * @return
	 */
	@RequestMapping(params = "checkUserName")
	@ResponseBody
	public ValidForm checkUserName(HttpServletRequest request) {

		// 页面数据取得
		String userName = oConvertUtils
				.getString(request.getParameter("param")); // 页面输入部门名称
		String userId = oConvertUtils.getString(request.getParameter("userId"));

		// 检索数据库
		CriteriaQuery cq = new CriteriaQuery(TSUser.class);
		cq.eq("userName", userName);
		cq.eq("isdelete", (short) 0);

		if (userId != null && !userId.isEmpty()) {
			cq.notEq("id", userId);
		}

		cq.add();

		// 重复check
		List<TSUser> sList = this.systemService.getListByCriteriaQuery(cq,
				false);

		ValidForm vf = new ValidForm();
		if (sList != null && sList.size() > 0) {
			vf.setInfo("用户名称已被录入，不能重复录入！");
			vf.setStatus("n");
		}
		return vf;
	}
}