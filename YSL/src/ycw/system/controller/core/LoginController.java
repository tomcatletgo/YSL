package ycw.system.controller.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import core.common.model.json.AjaxJson;
import core.constant.Globals;
import core.extend.datasource.DataSourceContextHolder;
import core.extend.datasource.DataSourceType;
import core.util.ContextHolderUtils;
import core.util.IpUtil;
import core.util.NumberComparator;
import core.util.PasswordUtil;
import core.util.ResourceUtil;
import ycw.om.form.common.NSCommonUtils;
import ycw.om.form.common.NSSmsUtils;
import ycw.system.manager.ClientManager;
import ycw.system.pojo.base.Client;
import ycw.system.pojo.base.TSConfig;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.pojo.base.TSUser;
import ycw.system.service.SystemService;
import ycw.system.service.UserService;
import ycw.system.vo.PicVerifyCodeVo;

/**
 * 登陆初始化控制器
 * 
 * @author 张代浩
 * 
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {
	private Logger log = Logger.getLogger(LoginController.class);
	private SystemService systemService;
	private UserService userService;


	
	private String message = null;
	private Map<Integer, List<TSFunction>> shortcutFunctionMap = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}

	/**
	 * admin账户密码初始化
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pwdInit")
	public ModelAndView pwdInit(HttpServletRequest request) {
		ModelAndView modelAndView = null;
		TSUser user = new TSUser();
		user.setUserName("admin");
		String newPwd = "123456";
		userService.pwdInit(user, newPwd);
		modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_dhc);
		AjaxJson j = new AjaxJson();
		// if (!this.checkRandCode(req, session)) {
		// j.setMsg("验证码错误");
		// j.setSuccess(false);
		// return j;
		// }
		int users = userService.getList(TSUser.class).size();
		if (users == 0) {
			j.setMsg("无用户数据!");
			j.setSuccess(false);
			return j;
		}

		TSUser u = userService.checkUserExits(user);
		if (u == null) {
			j.setMsg("用户名或密码错误!");
			j.setSuccess(false);
			return j;
		}
		// 校验用户是否已经审核通过
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isUserName4Check", user.getUserName());
		map.put("begin", 0);
		map.put("end", 10);
		List results = userService.getUser(map);
		

		TSUser userDisable = userService.checkUserDisable(user);
		/*
		 * if (userDisable == null) { j.setMsg("该用户名已被停用!"); j.setSuccess(false); return
		 * j; }
		 */

		message = "用户: " + "[" + u.getUserName() + "]" + "登录成功";
		Client client = new Client();
		client.setIp(IpUtil.getIpAddr(req));
		client.setLogindatetime(new Date());
		client.setUser(u);
		List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", userDisable.getId());
		client.setUserRoleList(rUsers);
		ClientManager.getInstance().addClinet(session.getId(), client);
		// 添加登陆日志
		systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(params = "login")
	public String login(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_dhc);
		TSUser user = ResourceUtil.getSessionUserName();
		// String roles = "";
		if (user != null) {
			Client client = (Client) ClientManager.getInstance().getClient(session.getId());
			// List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class,
			// "TSUser.id", user.getId());
			List<TSRoleUser> rUsers = client.getUserRoleList();

			/*
			 * List<TSMessage> msgs = systemService.getListByCriteriaQuery(cq, false);
			 * request.setAttribute("unreadMessageCount", msgs.size());
			 */
			request.setAttribute("userId", user.getId());
			// request.setAttribute("departName", user.getTSDepart().getDepartname());
			Map<String, Object> pMap = new HashMap<String, Object>();

			String businessesName = "";
			/*boolean isAdminFlag = ResourceUtil.isUserAdmin();
			if (!isAdminFlag) {
				String businessesRowId = user.getBusinessesRowId();
				pMap.put("businessesRowId", businessesRowId);
				List results = businessesService.findBusinessesList(pMap);

				if (NSCommonUtils.isListNotEmpty(results)) {
					NSBusinessesInfoEntity biEntity = (NSBusinessesInfoEntity) results.get(0);
					businessesName = biEntity.getBusinessesName();
				}

			} else {
				pMap = new HashMap<String, Object>();

				pMap.put("resourcesType", "11");
				List results = resourcesService.findResourcesList(pMap);
				if (NSCommonUtils.isListNotEmpty(results)) {
					NSResourcesEntity rEntity = (NSResourcesEntity) results.get(0);
					businessesName = rEntity.getValue();
				}

			}*/

			request.setAttribute("businessesName", businessesName);
			request.setAttribute("userName", user.getUserName());
			request.getSession().setAttribute("CKFinder_UserRole", "admin");
			// 获取一级菜单列表
			request.setAttribute("primaryMenuList", getPrimaryMenu(rUsers));
			// 默认风格
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
			// 要添加自己的风格，复制下面三行即可
			if (StringUtils.isNotEmpty(indexStyle) && indexStyle.equalsIgnoreCase("blue")) {
				return "main/shortcut_main_zhida";
			}
			// if (StringUtils.isNotEmpty(indexStyle) &&
			// indexStyle.equalsIgnoreCase("green")) {
			// return "main/shortcut_main_green";
			// }
			return "main/main";
		} else {
			return "login/login";
		}
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
		shortcutFunctionMap = null;

		systemService.addLog("用户: " + user.getUserName() + "已退出", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));

		return modelAndView;
	}

	/**
	 * 菜单跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}

		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/left");
	}

	/**
	 * 获取权限的map
	 * 
	 * @param user
	 * @return Map
	 */
	private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
		Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
		Map<String, TSFunction> loginActionlist = getUserFunction(user);
		if (loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			for (TSFunction function : allFunctions) {
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0, new ArrayList<TSFunction>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TSFunction>> c = functionMap.values();
			for (List<TSFunction> list : c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		return functionMap;
	}

	/**
	 * 获取用户菜单列表
	 * 
	 * @param user
	 * @return Map
	 */
	private Map<String, TSFunction> getUserFunction(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client.getFunctions() == null) {
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();

				if ("admin".equals(role.getRoleCode())) {
					List<TSFunction> functionList = systemService.loadAll(TSFunction.class);
					for (TSFunction f : functionList) {
						loginActionlist.put(f.getId(), f);
					}
					break;
				}

				List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id",
						role.getId());
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = roleFunction.getTSFunction();
					loginActionlist.put(function.getId(), function);
				}
			}
			client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
	}

	/**
	 * 首页跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		String posRecordUrl = ResourceUtil.getConfigByName("posRecordUrl");
		request.setAttribute("posRecordUrl", posRecordUrl);
		return new ModelAndView("main/home");
	}

	/**
	 * 无权限页面提示跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}

	/**
	 * @Title: top
	 * @Description: bootstrap头部菜单请求
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/bootstrap_top");
	}

	/**
	 * @Title: top
	 * @author gaofeng
	 * @Description: shortcut头部菜单请求
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(params = "shortcut_top_green")
	public ModelAndView shortcut_top_green(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top_green");
	}

	@RequestMapping(params = "shortcut_top_blue")
	public ModelAndView shortcut_top_blue(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top_blue");
	}

	@RequestMapping(params = "shortcut_top_zhida")
	public ModelAndView shortcut_top_zhida(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top_zhida");
	}

	/**
	 * @Title: top
	 * @author:gaofeng
	 * @Description:shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
	 * @param request
	 * @return AjaxJson
	 */
	public String getPrimaryMenu(List<TSRoleUser> rUsers) {
		// 获取一级菜单列表
		Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
		if (shortcutFunctionMap != null) {
			// 将更新前的菜单数据传过来
			functionMap = shortcutFunctionMap;
		} else {
			functionMap = getPrimaryMenuBase(rUsers);
		}
		String floor = "";
		List<TSFunction> primaryMenu = null;
		Iterator<Entry<Integer, List<TSFunction>>> it = functionMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			primaryMenu = (List<TSFunction>) pairs.getValue();

			for (TSFunction function : primaryMenu) {
				// core.util.LogUtil.info(function.getFunctionName());
				// core.util.LogUtil.info(function.getFunctionLevel());
				if (function.getFunctionLevel() == 0) {

					// 文字内容
					String strchar = "";
					strchar = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>"
							+ function.getFunctionName() + "</div>";
					// 其他的为默认通用的图片模式
					String s = "";
					if (function.getFunctionName().length() >= 5 && function.getFunctionName().length() < 7) {
						s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#f2f9f9;font-size:12px;'><span style='letter-spacing:-1px;'>"
								+ function.getFunctionName() + "</span></div>";
					} else if (function.getFunctionName().length() < 5) {
						s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#f2f9f9;font-size:12px;'>"
								+ function.getFunctionName() + "</div>";
					} else if (function.getFunctionName().length() >= 7) {
						s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#f2f9f9;font-size:12px;'><span style='letter-spacing:-1px;'>"
								+ function.getFunctionName().substring(0, 6) + "</span></div>";
					}

					if ("工作日报".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/gzrb.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/gzrb_up.png' style='display: none;' />"
								+ s + " </li> ";
					} else if ("文件管理".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/ldwjgl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldwjgl1.png' style='display: none;' />"
								+ s + " </li> ";
					} else if ("系统管理".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/ldxtgl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldxtgl1.png' style='display: none;' />"
								+ s + " </li> ";
					} else if ("人员车辆".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/ldrycl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldrycl1.png' style='display: none;' />"
								+ s + " </li> ";
					} else if ("营运管理".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/ldyyfx.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldyyfx1.png' style='display: none;' />"
								+ s + " </li> ";
					}
					// else if ("任务管理".equals(function.getFunctionName())) {
					//
					// floor += " <li style='position: relative;'><img class='imag1'
					// src='plug-in/login/images/ldrwgl.png' /> "
					// + " <img class='imag2' src='plug-in/login/images/ldrwgl.png' style='display:
					// none;' />"
					// +s+ " </li> ";
					// } else if ("考勤管理".equals(function.getFunctionName())) {
					//
					// floor += " <li style='position: relative;'><img class='imag1'
					// src='plug-in/login/images/kqgl.png' /> "
					// + " <img class='imag2' src='plug-in/login/images/kqgl_up.png' style='display:
					// none;' />"
					// +s+ " </li> ";
					// }
					else if ("车辆轨迹".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/ldclgj.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldclgj1.png' style='display: none;' />"
								+ s + " </li> ";
					} else if ("设备管理".equals(function.getFunctionName())) {

						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "')  style='position: relative;'><img class='imag1' src='plug-in/login/images/ldsbgl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldsbgl1.png' style='display: none;' />"
								+ s + " </li> ";
					}
					// else if ("工作流管理".equals(function.getFunctionName())) {
					//
					// floor += " <li style='position: relative;'><img class='imag1'
					// src='plug-in/login/images/gzlgl.png' /> "
					// + " <img class='imag2' src='plug-in/login/images/gzlgl_up.png'
					// style='display: none;' />"
					// +s+ " </li> ";
					// }
					else {
						// // 其他的为默认通用的图片模式
						// String s = "";
						// if (function.getFunctionName().length() >= 5
						// && function.getFunctionName().length() < 7) {
						// s = "<div style='width:67px;position:
						// absolute;top:40px;text-align:center;color:#f2f9f9;font-size:12px;'><span
						// style='letter-spacing:-1px;'>"
						// + function.getFunctionName()
						// + "</span></div>";
						// } else if (function.getFunctionName().length() < 5) {
						// s = "<div style='width:67px;position:
						// absolute;top:40px;text-align:center;color:#f2f9f9;font-size:12px;'>"
						// + function.getFunctionName() + "</div>";
						// } else if (function.getFunctionName().length() >= 7) {
						// s = "<div style='width:67px;position:
						// absolute;top:40px;text-align:center;color:#f2f9f9;font-size:12px;'><span
						// style='letter-spacing:-1px;'>"
						// + function.getFunctionName()
						// .substring(0, 6) + "</span></div>";
						// }
						floor += " <li onclick=updateName('" + function.getFunctionName()
								+ "') style='position: relative;'><img class='imag1' src='plug-in/login/images/ldsbgl.png' /> "
								+ " <img class='imag2' src='plug-in/login/images/ldsbgl1.png' style='display: none;' />"
								+ s + "</li> ";
					}
				}
			}
		}
		return floor;
	}

	/**
	 * @Title: top
	 * @author:gaofeng
	 * @Description:shortcut头部菜单一级菜单列表，实现动态控制一级菜单列表的基础方法
	 * @param request
	 * @return AjaxJson
	 */
	public Map<Integer, List<TSFunction>> getPrimaryMenuBase(List<TSRoleUser> rUsers) {
		// 获取一级菜单列表
		Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
		Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();

			if ("admin".equals(role.getRoleCode())) {
				List<TSFunction> functionList = systemService.loadAll(TSFunction.class);
				for (TSFunction f : functionList) {
					loginActionlist.put(f.getId(), f);
				}
				break;
			}

			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id",
					role.getId());
			for (TSRoleFunction roleFunction : roleFunctionList) {
				TSFunction function = roleFunction.getTSFunction();
				loginActionlist.put(function.getId(), function);
			}
		}

		if (loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			for (TSFunction function : allFunctions) {
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0, new ArrayList<TSFunction>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TSFunction>> c = functionMap.values();
			for (List<TSFunction> list : c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		// 将更新前的菜单数据赋值过去
		shortcutFunctionMap = functionMap;
		return functionMap;
	}

	/**
	 * ajax判断用户登录
	 * 
	 * @param req
	 * @param driver
	 * @return
	 */
	@RequestMapping(params = "messageLogin")
	@ResponseBody
	public AjaxJson messageLogin(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String strName = "";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());
		if (username != null && username != "" && password != null && password != "") {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);
			map.put("password", password);
			strName = userService.messageLogin(map);
		}
		j.setMsg(strName);
		return j;
	}

	/**
	 * ajax判断用户登录
	 * 
	 * @param req
	 * @param driver
	 * @return
	 */
	@RequestMapping(params = "genAndSendVerifyCode")
	@ResponseBody
	public AjaxJson genAndSendVerifyCode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String userIp = NSCommonUtils.getIpAddress(request);
			String userTel = request.getParameter("userTel");

//			// 新增ip地址调用次数限制   (一分钟之内最多只可以发送5条短信)
//			int sendTimes = smsInfoDao.findSendTimesPMin(userIp);
//			
//			if(sendTimes > 5) {
//				j.setMsg("同一客户端不可频繁发送短信!!!");
//				j.setSuccess(false);
//				return j;
//			}
			
			
			
			
			// 图形校验码校验
			String sessionPicVerifyCode = (String) request.getSession().getAttribute("picVerifyCode");
			String picVerifyCode = request.getParameter("picVerifyCode");
			if (StringUtils.isEmpty(sessionPicVerifyCode)) {
				j.setMsg("图形验证码错误.请重新获取!!!");
				j.setSuccess(false);
				return j;
			}
			if (!sessionPicVerifyCode.equals(picVerifyCode)) {
				j.setMsg("图形验证码错误.请重新输入!!!");
				j.setSuccess(false);
				return j;
			}

			/*NSUserVerifyInfoEntity verifyEntity = businessesService.selectVerifyCodeByTel(userTel);
			if (verifyEntity != null) {
				// 同一手机号 一分钟之内不能重复发送
				Date time1 = new Date();
				String time2 = verifyEntity.getCreateTime();
				long dValue = NSCommonUtils.getTimeDvalue(time1, time2);
				if (dValue < 60) {
					j.setMsg("不能频繁发送短信验证码!!!");
					j.setSuccess(false);
					return j;
				}
			}*/

			// 生成验证码
			String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));

			String templateParam = "{\"verifyCode\":\"" + verifyCode + "\"}";
			String templateCode = "SMS_117300825";
			String signName = "媒货网易货app";
			// 调用阿里云发送验证码
			SendSmsResponse smsRes = NSSmsUtils.sendSmsInfo(userTel, signName, templateCode, templateParam);
			String requestId = smsRes.getRequestId();
			String code = smsRes.getCode();
			String message = smsRes.getMessage();
			String bizId = smsRes.getBizId();
/*
			// 添加短信发送表信息
			NSSmsInfoEntity addEntity = new NSSmsInfoEntity();
			addEntity.setTelNum(userTel);
			addEntity.setInSignname(signName);
			addEntity.setInTemplatecode(templateCode);
			addEntity.setInTemplateparam(templateParam);
			addEntity.setOutBizid(bizId);
			addEntity.setOutCode(code);
			addEntity.setOutMessage(message);
			addEntity.setOutRequestid(requestId);
			addEntity.setClinetIpAddress(userIp);
			smsInfoDao.addSendSmsInfo(addEntity);

			if (!"OK".toLowerCase().equals(code.toLowerCase())) {
				j.setMsg("验证码发送错误!!!");
				j.setSuccess(false);
				log.error("genAndSendVerifyCode error :SendSmsResponse code is :" + code);
				return j;
			}
*/
/*			int rValue = businessesService.saveTelAndVerifyCode(userTel, verifyCode);
			if (rValue > 0) {
				//j.setMsg(verifyCode);
				j.setSuccess(true);
			} else {
				j.setMsg("验证码发送错误!!!");
				j.setSuccess(false);
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			j.setMsg("验证码发送错误!!!");
			e.printStackTrace();
			log.error("genAndSendVerifyCode error :", e);
		}
		return j;
	}
	
	
	@RequestMapping(params = "genPicVerifyCode")
	@ResponseBody
	public AjaxJson genPicVerifyCode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
		    String sessionId = request.getSession().getId();
		    
		    // 生成验证码
		 	String picVerifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
		 	PicVerifyCodeVo pVo = new PicVerifyCodeVo();
		 	pVo.setSessionId(sessionId);
		 	pVo.setPicVerifyCode(picVerifyCode);
		 	j.setObj(pVo);
		 	request.getSession().setAttribute("picVerifyCode", picVerifyCode);
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			e.printStackTrace();
			log.error("genPicVerifyCode error :", e);
		}
		return j;
	}
	
	
	
	
	
	
	@RequestMapping(params = "checkVerifyCode")
	@ResponseBody
	public AjaxJson checkVerifyCode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			
			String userTel = request.getParameter("userTel");
			String verifyCode = request.getParameter("verifyCode");
			
		
			
			/*// 查询校验码
			NSUserVerifyInfoEntity verifyEntity = businessesService.selectVerifyCodeByTel(userTel);
			if (verifyEntity != null) {
				// 计算校验码与当前时间的差值. 如果查过5分钟,则校验失败
				Date time1 = new Date();
				String time2 = verifyEntity.getCreateTime();
				long dValue = NSCommonUtils.getTimeDvalue(time1, time2);
				if (dValue / 60 > 5) {
					j.setMsg("短信验证码已失效.请重新申请!!!");
					j.setSuccess(false);
				} else {
					if (verifyCode.equals(verifyEntity.getVerifyCode())) {
						j.setSuccess(true);
						// 验证成功后,清空图形验证码
						request.getSession().setAttribute("picVerifyCode", "");
					} else {
						j.setMsg("短信验证码错误.请重新填写!!!");
						j.setSuccess(false);
					}

				}

			} else {
				j.setMsg("无验证码信息.请重新申请!!!");
				j.setSuccess(false);
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			j.setSuccess(false);
			e.printStackTrace();
			log.error("checkVerifyCode error :", e);
		}
		return j;
	}
}
