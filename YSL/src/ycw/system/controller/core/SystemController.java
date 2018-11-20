package ycw.system.controller.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import core.common.controller.BaseController;
import core.common.hibernate.qbc.CriteriaQuery;
import core.common.model.common.UploadFile;
import core.common.model.json.AjaxJson;
import core.common.model.json.ComboTree;
import core.common.model.json.DataGrid;
import core.common.model.json.Highchart;
import core.common.model.json.TreeGrid;
import core.common.model.json.ValidForm;
import core.constant.Globals;
import core.util.DataUtils;
import core.util.MyClassLoader;
import core.util.ResourceUtil;
import core.util.SetListSort;
import core.util.StringUtil;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import tag.vo.easyui.ComboTreeModel;
import tag.vo.easyui.TreeGridModel;
//import ycw.activiti.service.FlowService;
//import ycw.module.entity.TBDaily;
//import ycw.module.entity.TBFeedback;
import ycw.system.manager.ClientManager;
import ycw.system.manager.ClientSort;
import ycw.system.pojo.base.Client;
import ycw.system.pojo.base.TSDepart;
import ycw.system.pojo.base.TSDocument;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.pojo.base.TSType;
import ycw.system.pojo.base.TSTypegroup;
import ycw.system.pojo.base.TSUser;
import ycw.system.pojo.base.TSVersion;
import ycw.system.service.SystemService;
import ycw.system.service.TypeService;
import ycw.system.service.UserService;

/**
 * 类型字段处理类
 * 
 * @author 张代浩
 * 
 */
@Controller
@RequestMapping("/systemController")
public class SystemController extends BaseController {
	private static final Logger logger = Logger.getLogger(SystemController.class);
	private UserService userService;
	private SystemService systemService;
	private String message;

	private TypeService typeService;
	
	 @Autowired
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(params = "druid")
	public ModelAndView druid() {
		return new ModelAndView(new RedirectView("druid/index.html"));
	}
	
//    @Autowired
//    private FlowService flowService;
    
	/**
	 * 类型字典列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "typeGroupTabs")
	public ModelAndView typeGroupTabs(HttpServletRequest request) {
		List<TSTypegroup> typegroupList = systemService.loadAll(TSTypegroup.class);
		request.setAttribute("typegroupList", typegroupList);
		return new ModelAndView("system/type/typeGroupTabs");
	}

	/**
	 * 类型分组列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "typeGroupList")
	public ModelAndView typeGroupList(HttpServletRequest request) {
		return new ModelAndView("system/type/typeGroupList");
	}

	/**
	 * 类型列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "typeList")
	public ModelAndView typeList(HttpServletRequest request) {
		String typegroupid = request.getParameter("typegroupid");
		TSTypegroup typegroup = systemService.getEntity(TSTypegroup.class, typegroupid);
		request.setAttribute("typegroup", typegroup);
		return new ModelAndView("system/type/typeList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "typeGroupGrid")
	public void typeGroupGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Map<String, Object> map = new HashMap<String, Object>();
//	CriterIAQUERY CQ = NEW CRITERIAQUERY(TSTYPEGROUP.CLASS, DATAGRID);
//		THis.systemService.getDataGridReturn(cq, true);
		List<TSTypegroup> data=this.typeService.getTypeList();
		dataGrid.setResults(data);
		TagUtil.datagrid(response, dataGrid);
	}
	
    /**
     * 跳转到类型页面
     * 
     * @param request
     * @return
     */
	@RequestMapping(params = "goTypeGrid")
	public ModelAndView goTypeGrid(HttpServletRequest request) {
		String typegroupid = request.getParameter("typegroupid");
        request.setAttribute("typegroupid", typegroupid);
		return new ModelAndView("system/type/typeListForTypegroup");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "typeGrid")
	public void typeGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String typegroupid = request.getParameter("typegroupid");
		String typename = request.getParameter("typename");
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("typegroupid", typegroupid);
	      map.put("typename", typename);
	      

		int page = dataGrid.getPage();
		int rows = dataGrid.getRows();

		map.put("begin", page * rows - rows);
		map.put("end", rows);
	      
	     List li = typeService.getTypes(map);
	     dataGrid.setResults(li);
//		CriteriaQuery cq = new CriteriaQuery(TSType.class, dataGrid);
//		cq.eq("TSTypegroup.id", typegroupid);
//		cq.like("typename", typename);
//		cq.add();
//		this.systemService.getDataGridReturn(cq, true);
	     
	 	int count = this.typeService.getTypesCount(map);
		dataGrid.setTotal(count);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 显示统计报表
	 * 
	 * @param request
	 * @param reportType
	 * @param response
	 * @return List<Highchart>
	 */
//	@RequestMapping(params = "getHomeChartInfo")
//	@ResponseBody
//	public List<Highchart> getHomeChartInfo(HttpServletRequest request,String reportType, HttpServletResponse response) {
//		List<Highchart> list = new ArrayList<Highchart>();
//		Highchart hcHandling = new Highchart();
//		Highchart hcSolved = new Highchart();
//		List ltHandling = new ArrayList();
//		List ltSoloved = new ArrayList();
//		Timestamp tsStartOfToday = DataUtils.getTimestamp(DataUtils.startOfTodDay());
//		
//		TSUser user = ResourceUtil.getSessionUserName();
//		//获取待处理反馈意见
//		CriteriaQuery feedbackCqHandling = new CriteriaQuery(TBFeedback.class);
//		feedbackCqHandling.eq("tsFeedbackUser.id", user.getId());
//		feedbackCqHandling.eq("isComplete", "0");
//		feedbackCqHandling.eq("isCancel", "0");
//		feedbackCqHandling.add();
//		List<TBFeedback> feedbacksHandling = systemService.getListByCriteriaQuery(feedbackCqHandling, false);
//		//获取已处理反馈已经
//		CriteriaQuery feedbackCqSolved = new CriteriaQuery(TBFeedback.class);
//		feedbackCqSolved.eq("tsFeedbackUser.id", user.getId());
//		feedbackCqSolved.eq("isComplete", "1");
//		feedbackCqSolved.eq("isCancel", "0");
//		feedbackCqSolved.ge("completeTime", tsStartOfToday);
//		feedbackCqSolved.add();
//		List<TBFeedback> feedbacksSolved = systemService.getListByCriteriaQuery(feedbackCqSolved, false);
//		//获取待办任务
//		int taskHandlingSize = flowService.queryUnFinishedTaskSize(user.getId());
//		//获取已办任务
//		int taskSolvedSize = flowService.queryFinishedTaskSize(user.getId(), new Date(DataUtils.startOfTodDay()));
//		//获取今日日报
//		CriteriaQuery dailyCq = new CriteriaQuery(TBDaily.class);
//		dailyCq.eq("TSUser.id", user.getId());
//		dailyCq.eq("dateTime", tsStartOfToday);
//		dailyCq.add();
//		List<TBDaily> dailys = systemService.getListByCriteriaQuery(dailyCq, false);
//		
//		hcHandling.setName("待处理");
//		hcSolved.setName("已完成");
//		ltHandling.add(feedbacksHandling.size());
//		ltHandling.add(taskHandlingSize);
//		ltSoloved.add(feedbacksSolved.size());
//		ltSoloved.add(taskSolvedSize);
//		if(dailys.size()>0){
//			ltHandling.add(0);
//			ltSoloved.add(1);
//		}else{
//			ltHandling.add(1);
//			ltSoloved.add(0);
//		}
//		hcHandling.setData(ltHandling);
//		hcSolved.setData(ltSoloved);
//		list.add(hcHandling);
//		list.add(hcSolved);
//		return list;
//	}
	
	/**
	 * 类型字典树
	 * 
	 * @param request
	 * @param treegrid
	 * @return List<TreeGrid>
	 */
	@RequestMapping(params = "typeGridTree")
	@ResponseBody
	public List<TreeGrid> typeGridTree(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq;
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		if (treegrid.getId() != null) {
			cq = new CriteriaQuery(TSType.class);
			cq.eq("TSTypegroup.id", treegrid.getId().substring(1));
			cq.add();
			List<TSType> typeList = systemService.getListByCriteriaQuery(cq, false);
			for (TSType obj : typeList) {
				TreeGrid treeNode = new TreeGrid();
				treeNode.setId("T" + obj.getId());
				treeNode.setText(obj.getTypename());
				treeNode.setCode(obj.getTypecode());
				treeGrids.add(treeNode);
			}
		} else {
			cq = new CriteriaQuery(TSTypegroup.class);
			List<TSTypegroup> typeGroupList = systemService.getListByCriteriaQuery(cq, false);
			for (TSTypegroup obj : typeGroupList) {
				TreeGrid treeNode = new TreeGrid();
				treeNode.setId("G" + obj.getId());
				treeNode.setText(obj.getTypegroupname());
				treeNode.setCode(obj.getTypegroupcode());
				treeNode.setState("closed");
				treeGrids.add(treeNode);
			}
		}
		return treeGrids;
	}
	
	/**
	 * 删除类型分组或者类型（ID以G开头的是分组）
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delTypeGridTree")
	@ResponseBody
	public AjaxJson delTypeGridTree(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (id.startsWith("G")) {//分组
			TSTypegroup typegroup = systemService.getEntity(TSTypegroup.class, id.substring(1));
			message = "数据字典分组: " + typegroup.getTypegroupname() + "被删除成功";
			systemService.delete(typegroup);
		} else {
			TSType type = systemService.getEntity(TSType.class, id.substring(1));
			message = "数据字典类型: " + type.getTypename() + "被删除成功";
			systemService.delete(type);
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		//刷新缓存
		systemService.refleshTypeGroupCach();
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除类型分组
	 * 
	 * @param TSTypegroup typegroup
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delTypeGroup")
	@ResponseBody
	public AjaxJson delTypeGroup(TSTypegroup typegroup, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		typegroup = systemService.getEntity(TSTypegroup.class, typegroup.getId());
		message = "类型分组: " + typegroup.getTypegroupname() + "被删除成功";
		systemService.delete(typegroup);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		//刷新缓存
		systemService.refleshTypeGroupCach();
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除类型
	 * 
	 * @param TSType type
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delType")
	@ResponseBody
	public AjaxJson delType(TSType type, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
	String id = request.getParameter("id");
	//	type = systemService.getEntity(TSType.class, type.getId());
	    type=typeService.selectTypesid(id);
	    TSTypegroup ptype=typeService.selectParentType(id);
	    type.setTSTypegroup(ptype);
		try{
			typeService.deleteTypes(id);
			message = "类型: " + type.getTypename() + "被删除成功";
			//刷新缓存
//			systemService.refleshTypesCach(type);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			//e.printStackTrace();
			message = "类型: " + type.getTypename() + "已被使用，无法删除";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 检查分组代码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkTypeGroup")
	@ResponseBody
	public ValidForm checkTypeGroup(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String typegroupcode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<TSTypegroup> typegroups=systemService.findByProperty(TSTypegroup.class,"typegroupcode",typegroupcode);
		if(typegroups.size()>0&&!code.equals(typegroupcode))
		{
			v.setInfo("分组已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 添加类型分组
	 * 
	 * @param TSTypegroup typegroup
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveTypeGroup")
	@ResponseBody
	public AjaxJson saveTypeGroup(TSTypegroup typegroup, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(typegroup.getId())) {
			message = "类型分组: " + typegroup.getTypegroupname() + "被更新成功";
			userService.saveOrUpdate(typegroup);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "类型分组: " + typegroup.getTypegroupname() + "被添加成功";
			userService.save(typegroup);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		//刷新缓存
		systemService.refleshTypeGroupCach();
		j.setMsg(message);
		return j;
	}
	/**
	 * 检查类型代码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkType")
	@ResponseBody
	public ValidForm checkType(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String typecode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		String typeGroupId = oConvertUtils.getString(request.getParameter("typeGroupId"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		  map.put("typeGroupId", typeGroupId);
		  map.put("typecode", typecode);
//		StringBuilder hql = new StringBuilder("FROM ").append(TSType.class.getName()).append(" AS entity WHERE 1=1 ");
//		hql.append(" AND entity.TSTypegroup.id =  '").append(typeGroupId).append("'");
//		hql.append(" AND entity.typecode =  '").append(typecode).append("'");
//		CriteriaQuery cq = new CriteriaQuery(TSType.class);
//		cq.eq("TSTypegroup.id", typeGroupId);
//		cq.eq("typecode", typecode);
//		cq.add();
		List<Object> types = this.typeService.insertTypes(map);
		if (types.size() > 0 && !code.equals(typecode)) {
			v.setInfo("类型编码已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 添加类型
	 * 
	 * @param TSType type
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveType")
	@ResponseBody
	public AjaxJson saveType(TSType type, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String typecode = request.getParameter("typecode");
		String typename = request.getParameter("typename");
		if (StringUtil.isNotEmpty(type.getId())) {
			message = "类型: " + type.getTypename() + "被更新成功";
			typeService.updateTypes(type);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "类型: " + type.getTypename() + "被添加成功";
			//userService.save(type);
			type.setId(UUID.randomUUID().toString().replaceAll("-",""));
		     typeService.saveTypes(type);
		     
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		//刷新缓存
//		systemService.refleshTypesCach(type);
		j.setMsg(message);
		return j;
	}

	

	/**
	 * 类型分组列表页面跳转
	 * 
	 * @param typegroup
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "aouTypeGroup")
	public ModelAndView aouTypeGroup(TSTypegroup typegroup, HttpServletRequest req) {
		if (typegroup.getId() != null) {
			typegroup = systemService.getEntity(TSTypegroup.class, typegroup.getId());
			req.setAttribute("typegroup", typegroup);
		}
		return new ModelAndView("system/type/typegroup");
	}

	/**
	 * 类型列表页面跳转
	 * 
	 * @param type
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdateType")
	public ModelAndView addorupdateType(TSType type, HttpServletRequest req) {
		String typegroupid = req.getParameter("typegroupid");
		req.setAttribute("typegroupid", typegroupid);
		if (StringUtil.isNotEmpty(type.getId())) {
		//type = systemService.getEntity(TSType.class, type.getId());
		 TSType typ = typeService.selectTypes(type.getId());
			req.setAttribute("type", typ);
		}
		return new ModelAndView("system/type/type");
	}

	/*
	 * *****************部门管理操作****************************
	 */

	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	public ModelAndView depart() {
		return new ModelAndView("system/depart/departList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridDepart")
	public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除部门
	 * 
	 * @param depart
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delDepart")
	@ResponseBody
	public AjaxJson delDepart(TSDepart depart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		depart = systemService.getEntity(TSDepart.class, depart.getId());
		message = "部门: " + depart.getDepartname() + "被删除成功";
		systemService.delete(depart);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * 添加部门
	 * 
	 * @param depart
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveDepart")
	@ResponseBody
	public AjaxJson saveDepart(TSDepart depart, HttpServletRequest request) {
		// 设置上级部门
		String pid = request.getParameter("TSPDepart.id");
		if (pid.equals("")) {
			depart.setTSPDepart(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(depart.getId())) {
			message = "部门: " + depart.getDepartname() + "被更新成功";
			userService.saveOrUpdate(depart);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "部门: " + depart.getDepartname() + "被添加成功";
			userService.save(depart);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 部门列表页面跳转
	 * 
	 * @param depart
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdateDepart")
	public ModelAndView addorupdateDepart(TSDepart depart, HttpServletRequest req) {
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);
		if (depart.getId() != null) {
			depart = systemService.getEntity(TSDepart.class, depart.getId());
			req.setAttribute("depart", depart);
		}
		return new ModelAndView("system/depart/depart");
	}

	/**
	 * 父级权限列表
	 * 
	 * @param request
	 * @param comboTree
	 * @return List<ComboTree>
	 * @return
	 */
	@RequestMapping(params = "getSubDepart")
	@ResponseBody
	public List<ComboTree> getSubDepart(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		//session中获取当前用户bean
		TSUser user = ResourceUtil.getSessionUserName();
		//以用户部门Id作为查询条件检索数据库
		cq.eq("id", user.getTSDepart().getId());
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		if (StringUtil.isEmpty(comboTree.getId())) {
			cq.isNull("TSPDepart.id");
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		cq.add();
		List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees = systemService.comTree(departsList, comboTree);
		return comboTrees;

	}

	/**
	 * 父级权限列表
	 * 
	 * @param request
	 * @param comboTree
	 * @return List<ComboTree>
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		if (StringUtil.isNotEmpty(comboTree.getId())) {
			cq.eq("TSPDepart.id", comboTree.getId());
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		if (StringUtil.isEmpty(comboTree.getId())) {
			//cq.isNull("TSPDepart.id");
			TSUser user = ResourceUtil.getSessionUserName();
			String userDepartId = user.getTSDepart().getId();
			cq.eq("id", userDepartId);
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		cq.eq("isDelete", "0");
		cq.add();
		List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees = systemService.comTree(departsList, comboTree);
		return comboTrees;
	}
	
	/*
	 * *****************角色管理操作****************************
	 */
	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	public ModelAndView role() {
		return new ModelAndView("system/role/roleList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridRole")
	public void datagridRole(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delRole")
	@ResponseBody
	public AjaxJson delRole(TSRole role, String ids, HttpServletRequest request) {
		message = "角色: " + role.getRoleName() + "被删除成功";
		AjaxJson j = new AjaxJson();
		role = systemService.getEntity(TSRole.class, role.getId());
		userService.delete(role);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 角色录入
	 * 
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveRole")
	@ResponseBody
	public AjaxJson saveRole(TSRole role, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (role.getId() != null) {
			message = "角色: " + role.getRoleName() + "被更新成功";
			userService.saveOrUpdate(role);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "角色: " + role.getRoleName() + "被添加成功";
			userService.saveOrUpdate(role);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 角色列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		Integer roleid = oConvertUtils.getInt(request.getParameter("roleid"), 0);
		request.setAttribute("roleid", roleid);
		return new ModelAndView("system/role/roleList");
	}

	/**
	 * 设置权限
	 * 
	 * @param role
	 * @param request
	 * @param comboTree
	 * @return List<ComboTree>
	 */
	@RequestMapping(params = "setAuthority")
	@ResponseBody
	public List<ComboTree> setAuthority(TSRole role, HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (comboTree.getId() != null) {
			cq.eq("TFunction.functionid", oConvertUtils.getInt(comboTree.getId(), 0));
		}
		if (comboTree.getId() == null) {
			cq.isNull("TFunction");
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		Integer roleid = oConvertUtils.getInt(request.getParameter("roleid"), 0);
		List<TSFunction> loginActionlist = new ArrayList<TSFunction>();// 已有权限菜单
		role = this.systemService.get(TSRole.class, roleid);
		if (role != null) {
			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = (TSFunction) roleFunction.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "functionName", "TSFunctions");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel, loginActionlist);
		return comboTrees;
	}

	/**
	 * 更新权限
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(params = "updateAuthority")
	public String updateAuthority(HttpServletRequest request) {
		Integer roleid = oConvertUtils.getInt(request.getParameter("roleid"), 0);
		String rolefunction = request.getParameter("rolefunctions");
		TSRole role = this.systemService.get(TSRole.class, roleid);
		List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
		systemService.deleteAllEntitie(roleFunctionList);
		String[] roleFunctions = null;
		if (rolefunction != "") {
			roleFunctions = rolefunction.split(",");
			for (String s : roleFunctions) {
				TSRoleFunction rf = new TSRoleFunction();
				TSFunction f = this.systemService.get(TSFunction.class, Integer.valueOf(s));
				rf.setTSFunction(f);
				rf.setTSRole(role);
				this.systemService.save(rf);
			}
		}
		return "system/role/roleList";
	}

	/**
	 * 角色页面跳转
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdateRole")
	public ModelAndView addorupdateRole(TSRole role, HttpServletRequest req) {
		if (role.getId() != null) {
			role = systemService.getEntity(TSRole.class, role.getId());
			req.setAttribute("role", role);
		}
		return new ModelAndView("system/role/role");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "operate")
	public ModelAndView operate(HttpServletRequest request) {
		String roleid = request.getParameter("roleid");
		request.setAttribute("roleid", roleid);
		return new ModelAndView("system/role/functionList");
	}

	/**
	 * 权限操作列表
	 * 
	 * @param request
	 * @param treegrid
	 * @return
	 */
	@RequestMapping(params = "setOperate")
	@ResponseBody
	public List<TreeGrid> setOperate(HttpServletRequest request, TreeGrid treegrid) {
		String roleid = request.getParameter("roleid");
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (treegrid.getId() != null) {
			cq.eq("TFunction.functionid", oConvertUtils.getInt(treegrid.getId(), 0));
		}
		if (treegrid.getId() == null) {
			cq.isNull("TFunction");
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		Collections.sort(functionList, new SetListSort());
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setRoleid(roleid);
		treeGrids = systemService.treegrid(functionList, treeGridModel);
		return treeGrids;
	}

	/**
	 * 操作录入
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveOperate")
	@ResponseBody
	public AjaxJson saveOperate(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String fop = request.getParameter("fp");
		String roleid = request.getParameter("roleid");
		// 录入操作前清空上一次的操作数据
		clearp(roleid);
		String[] fun_op = fop.split(",");
		String aa = "";
		String bb = "";
		// 只有一个被选中
		if (fun_op.length == 1) {
			bb = fun_op[0].split("_")[1];
			aa = fun_op[0].split("_")[0];
			savep(roleid, bb, aa);
		} else {
			// 至少2个被选中
			for (int i = 0; i < fun_op.length; i++) {
				String cc = fun_op[i].split("_")[0]; // 操作id
				if (i > 0 && bb.equals(fun_op[i].split("_")[1])) {
					aa += "," + cc;
					if (i == (fun_op.length - 1)) {
						savep(roleid, bb, aa);
					}
				} else if (i > 0) {
					savep(roleid, bb, aa);
					aa = fun_op[i].split("_")[0]; // 操作ID
					if (i == (fun_op.length - 1)) {
						bb = fun_op[i].split("_")[1]; // 权限id
						savep(roleid, bb, aa);
					}

				} else {
					aa = fun_op[i].split("_")[0]; // 操作ID
				}
				bb = fun_op[i].split("_")[1]; // 权限id
			}
		}
		return j;
	}

	/**
	 * 更新操作
	 * 
	 * @param roleid
	 * @param functionid
	 * @param ids
	 */
	public void savep(String roleid, String functionid, String ids) {
		String hql = "from TRoleFunction t where" + " t.TSRole.id=" + roleid + " " + "and t.TFunction.functionid=" + functionid;
		TSRoleFunction rFunction = systemService.singleResult(hql);
		if (rFunction != null) {
			rFunction.setOperation(ids);
			systemService.saveOrUpdate(rFunction);
		}
	}

	/**
	 * 清空操作
	 * 
	 * @param roleid
	 */
	public void clearp(String roleid) {
		String hql = "from TRoleFunction t where" + " t.TSRole.id=" + roleid;
		List<TSRoleFunction> rFunctions = systemService.findByQueryString(hql);
		if (rFunctions.size() > 0) {
			for (TSRoleFunction tRoleFunction : rFunctions) {
				tRoleFunction.setOperation(null);
				systemService.saveOrUpdate(tRoleFunction);
			}
		}
	}

	/************************************** 版本维护 ************************************/

	/**
	 * 版本维护列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "versionList")
	public void versionList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSVersion.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除版本
	 * 
	 * @param version
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delVersion")
	@ResponseBody
	public AjaxJson delVersion(TSVersion version, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		version = systemService.getEntity(TSVersion.class, version.getId());
		message = "版本：" + version.getVersionName() + "被删除成功";
		systemService.delete(version);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		return j;
	}

	/**
	 * 版本添加跳转
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addversion")
	public ModelAndView addversion(HttpServletRequest req) {
		return new ModelAndView("system/version/version");
	}

	/**
	 * 保存版本
	 * 
	 * @param request
	 * @return AjaxJson
	 * @throws Exception
	 */
	@RequestMapping(params = "saveVersion", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveVersion(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		TSVersion version = new TSVersion();
		String versionName = request.getParameter("versionName");
		String versionCode = request.getParameter("versionCode");
		version.setVersionCode(versionCode);
		version.setVersionName(versionName);
		systemService.save(version);
		j.setMsg("版本保存成功");
		return j;
	}

	/**
	 * 新闻法规文件列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "documentList")
	public void documentList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDocument.class, dataGrid);
		String typecode = oConvertUtils.getString(request.getParameter("typecode"));
		cq.createAlias("TSType", "TSType");
		cq.eq("TSType.typecode", typecode);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除文档
	 * 
	 * @param document
	 * @param request
	 * @return AjaxJson
	 */
	@RequestMapping(params = "delDocument")
	@ResponseBody
	public AjaxJson delDocument(TSDocument document, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		document = systemService.getEntity(TSDocument.class, document.getId());
		message = "" + document.getDocumentTitle() + "被删除成功";
		userService.delete(document);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 文件添加跳转
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addFiles")
	public ModelAndView addFiles(HttpServletRequest req) {
		return new ModelAndView("system/document/files");
	}
	
	/**
	 * 保存文件
	 * 
	 * @param request
	 * @param response
	 * @param document
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response, TSDocument document) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		TSTypegroup tsTypegroup = systemService.getTypeGroup("fieltype", "文档分类");
		TSType tsType = systemService.getType("files","附件", tsTypegroup);
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));// 文件标题
		if (StringUtil.isNotEmpty(fileKey)) {
			document.setId(fileKey);
			document = systemService.getEntity(TSDocument.class, fileKey);
			document.setDocumentTitle(documentTitle);
		}
		document.setSubclassname(MyClassLoader.getPackPath(document));
		document.setCreatedate(DataUtils.gettimestamp());
		document.setTSType(tsType);
		UploadFile uploadFile = new UploadFile(request, document);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		document = systemService.uploadFile(uploadFile);
		attributes.put("url", document.getRealpath());
		attributes.put("fileKey", document.getId());
		attributes.put("name", document.getAttachmenttitle());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + document.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + document.getId());
		j.setMsg("文件添加成功");
		j.setAttributes(attributes);
		return j;
	}
	
	/**
	 * 在线用户列表
	 * 
	 * @param tSOnline
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridOnline")
	public void datagridOnline(Client tSOnline,HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		List<Client> onlines = new ArrayList<Client>();
		onlines.addAll(ClientManager.getInstance().getAllClient());
		dataGrid.setTotal(onlines.size());
		dataGrid.setResults(getClinetList(onlines,dataGrid));
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 获取当前页面的用户列表
	 * @param onlines
	 * @param dataGrid
	 * @return List<Client>
	 */
	private List<Client> getClinetList(List<Client> onlines, DataGrid dataGrid) {
		Collections.sort(onlines, new ClientSort());
		List<Client> result = new ArrayList<Client>();
		for (int i = (dataGrid.getPage() - 1) * dataGrid.getRows(); 
				i < onlines.size() && i < dataGrid.getPage() * dataGrid.getRows(); i++) {
			result.add(onlines.get(i));
		}
		return result;
	}
}
