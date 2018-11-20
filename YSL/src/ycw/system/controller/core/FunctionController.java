package ycw.system.controller.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import core.common.controller.BaseController;
import core.common.hibernate.qbc.CriteriaQuery;
import core.common.model.json.AjaxJson;
import core.common.model.json.ComboTree;
import core.common.model.json.DataGrid;
import core.common.model.json.TreeGrid;
import core.constant.Globals;
import core.util.ReflectHelper;
import core.util.ResourceUtil;
import core.util.StringUtil;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import tag.vo.easyui.ComboTreeModel;
import tag.vo.easyui.TreeGridModel;
import ycw.system.pojo.base.TSDepart;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSIcon;
import ycw.system.pojo.base.TSOperation;
import ycw.system.pojo.base.TSUser;
import ycw.system.service.FunctionService;
import ycw.system.service.SystemService;
import ycw.system.service.UserService;
import ycw.system.vo.FunctionVo;

/**
 * 菜单权限处理类
 * 
 * @author 张代浩
 * 
 */
@Controller
@RequestMapping("/functionController")
public class FunctionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FunctionController.class);
	private UserService userService;
	private SystemService systemService;
	private FunctionService functionService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "function")
	public ModelAndView function() {
		return new ModelAndView("system/function/functionList");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "operation")
	public ModelAndView operation(HttpServletRequest request, String functionId) {
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		request.setAttribute("functionId", functionId);
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		return new ModelAndView("system/operation/operationList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "opdategrid")
	public void opdategrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSOperation.class, dataGrid);
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		String functionId = oConvertUtils.getString(request.getParameter("functionId"));
		cq.eq("TSFunction.id", functionId);
		cq.add();
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除权限
	 * 
	 * @param TSFunction function
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(FunctionVo function, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		function = functionService.getById(function.getId());
		try{
			message = "菜单: " + function.getFunctionName() + "被删除成功";
//			systemService.updateBySqlString("delete from t_s_role_function where functionid='"
//					+ function.getId() + "'");
			functionService.deleteChildFunction(function.getId());
//			systemService.delete(function);
			functionService.deleteFunction(function.getId());
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			message = "请先清空该菜单下的子菜单！";
		}
		j.setMsg(message);

		// // 删除权限时先删除权限与角色之间关联表信息
		// List<TSRoleFunction> roleFunctions =
		// systemService.findByProperty(TSRoleFunction.class, "TSFunction.id",
		// function.getId());
		//
		// if (roleFunctions.size() > 0) {
		// j.setMsg("菜单已分配无法删除");
		//
		// }
		// else {
		// userService.delete(function);
		// systemService.addLog(message, Globals.Log_Type_DEL,
		// Globals.Log_Leavel_INFO);
		// }

		return j;
	}

	/**
	 * 删除操作
	 * 
	 * @param TSOperation operation
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delop")
	@ResponseBody
	public AjaxJson delop(TSOperation operation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		operation = systemService.getEntity(TSOperation.class, operation.getId());
		message = "操作: " + operation.getOperationname() + "被删除成功";
		userService.delete(operation);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		return j;
	}

	/**
	 * 权限录入
	 * 
	 * @param TSFunction function
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveFunction")
	@ResponseBody
	public AjaxJson saveFunction(TSFunction function, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		String functionOrder = function.getFunctionOrder();
		if (StringUtils.isEmpty(functionOrder)) {
			function.setFunctionOrder("0");
		}
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		if (function.getTSFunction().getId().equals("")) {
			function.setTSFunction(null);
		} else {
			TSFunction parent = functionService.getParentFunction(function.getTSFunction().getId());
			function.setFunctionLevel(Short.valueOf(parent.getFunctionLevel()
					+ 1 + ""));
		}
		if (StringUtil.isNotEmpty(function.getId())) {
			message = "权限: " + function.getFunctionName() + "被更新成功";
			functionService.updateFunction(function);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
			// ----------------------------------------------------------------
			// ----------------------------------------------------------------

			systemService.flushRoleFunciton(function.getId(), function);

			// ----------------------------------------------------------------
			// ----------------------------------------------------------------

		} else {
//			if (function.getFunctionLevel().equals(Globals.Function_Leave_ONE)) {
//				List<TSFunction> functionList = systemService.findByProperty(
//						TSFunction.class, "functionLevel", Globals.Function_Leave_ONE);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_ONE+ordre);
//				function.setFunctionOrder(function.getFunctionOrder());
//			} else {
//				List<TSFunction> functionList = systemService.findByProperty(
//						TSFunction.class, "functionLevel", Globals.Function_Leave_TWO);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_TWO+ordre);
//				function.setFunctionOrder(function.getFunctionOrder());
//			}
			function.setId(UUID.randomUUID().toString().replaceAll("-",""));
			message = "权限: " + function.getFunctionName() + "被添加成功";
			functionService.saveFunction(function);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		return j;
	}

	/**
	 * 操作录入
	 * 
	 * @param TSOperation operation
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveop")
	@ResponseBody
	public AjaxJson saveop(TSOperation operation, HttpServletRequest request) {
		String pid = request.getParameter("TSFunction.id");
		if (pid.equals("")) {
			operation.setTSFunction(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(operation.getId())) {
			message = "操作: " + operation.getOperationname() + "被更新成功";
			userService.saveOrUpdate(operation);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "操作: " + operation.getOperationname() + "被添加成功";
			userService.save(operation);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		return j;
	}

	/**
	 * 权限列表页面跳转
	 * 
	 * @param TSFunction function
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(FunctionVo function, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		List<TSFunction> fuinctionlist = functionService.getAllList();
		req.setAttribute("flist", fuinctionlist);
		List<TSIcon> iconlist = functionService.getIconList();
		req.setAttribute("iconlist", iconlist);
		if (functionid != null) {
			function = functionService.getById(functionid);
			req.setAttribute("function", function);

			TSFunction f = functionService.getParentFunction(function.getParentId());
			
			if(f != null){
				function.setTSFunction(f);
			}
			
		}
		if (function.getTSFunction() != null
				&& function.getTSFunction().getId() != null) {
			function.setFunctionLevel((short) 1);
			function.setTSFunction(functionService.getById(function.getTSFunction().getId()));
			req.setAttribute("function", function);
		}
		return new ModelAndView("system/function/function");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @param TSOperation operation
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdateop")
	public ModelAndView addorupdateop(TSOperation operation,
			HttpServletRequest req) {
		List<TSIcon> iconlist = systemService.getList(TSIcon.class);
		req.setAttribute("iconlist", iconlist);
		if (operation.getId() != null) {
			operation = systemService.getEntity(TSOperation.class,
					operation.getId());
			req.setAttribute("operation", operation);
		}
		String functionId = oConvertUtils.getString(req
				.getParameter("functionId"));
		req.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operation");
	}

	/**
	 * 权限列表
	 * 
	 * @param request
	 * @param treegrid
	 * @return List<TreeGrid>
	 */
	@RequestMapping(params = "functionGrid")
	@ResponseBody
	public List<TreeGrid> functionGrid(HttpServletRequest request,
			TreeGrid treegrid) {
//		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
//		String selfId = request.getParameter("selfId");
//		if(selfId != null){
//			cq.notEq("id", selfId);
//		}
//		if (treegrid.getId() != null) {
//			cq.eq("TSFunction.id", treegrid.getId());
//		}
//		if (treegrid.getId() == null) {
//			cq.isNull("TSFunction");
//		}
//		cq.addOrder("functionOrder", SortDirection.asc);
//		cq.add();
//		List<TSFunction> functionList = systemService.getListByCriteriaQuery(
//				cq, false);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("parentfunctionid", treegrid.getId());
		
		String selfId = request.getParameter("selfId");
		if(selfId != null){
			map.put("selfId", selfId);
		}
		List<TSFunction> functionList = functionService.getFunctionList(map);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIcon("iconPath");
		treeGridModel.setTextField("functionName");
		treeGridModel.setParentText("TSFunction_functionName");
		treeGridModel.setParentId("TSFunction_id");
		treeGridModel.setSrc("functionUrl");
		treeGridModel.setIdField("id");
		// 添加排序字段
		treeGridModel.setOrder("functionOrder");
//		treeGrids = systemService.treegrid(functionList, treeGridModel);
		treeGrids = functionService.treegrid(functionList, treeGridModel);
		return treeGrids;
	}

	/**
	 * 权限列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "functionList")
	@ResponseBody
	public void functionList(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class, dataGrid);
		String id = oConvertUtils.getString(request.getParameter("id"));
		cq.isNull("TSFunction");
		if (id != null) {
			cq.eq("TSFunction.id", id);
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 父级权限下拉菜单
	 * @param request
	 * @param comboTree
	 * @return List<ComboTree>
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request,
			ComboTree comboTree) {
////			CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
//			String selfId = request.getParameter("selfId");
////			if(!StringUtil.isEmpty(selfId)){
////				cq.notEq("id", selfId);
////			}
////			if (comboTree.getId() != null) {
////				cq.eq("TSPDepart.id", comboTree.getId());
////			}
////			if (comboTree.getId() == null) {
////				//cq.isNull("TSPDepart");
////				TSUser user = ResourceUtil.getSessionUserName();
////				String userDepartId = user.getTSDepart().getId();
////				cq.eq("id", userDepartId);
////			}
////			cq.add();
////			List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
//			
//			Map<String, Object> map = new HashMap<String, Object>();
//			if(!StringUtil.isEmpty(selfId)){
//				map.put("selfId", selfId);
//			}
//			if (comboTree.getId() == null) {
//				//cq.isNull("TSPDepart");
//				TSUser user = ResourceUtil.getSessionUserName();
//				String userDepartId = user.getTSDepart().getId();
//				map.put("departId", userDepartId);
//			}
//			List<TSDepart> departsList = departService.getComboTree(map);
//			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
////			ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");
////			comboTrees = systemService.ComboTree(departsList, comboTreeModel, null);
//			
////			List<ComboTree> trees = new ArrayList<ComboTree>();
//			for (TSDepart depart : departsList) {
//				comboTrees.add(tree(depart, selfId, true));
//			}
//			return comboTrees;
		
		
		
//		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
//		if(null != request.getParameter("selfId")){
//			cq.notEq("id", request.getParameter("selfId"));
//		}
//		if (comboTree.getId() != null) {
//			cq.eq("TSFunction.id", comboTree.getId());
//		}
//		if (comboTree.getId() == null) {
//			cq.isNull("TSFunction");
//		}
//		cq.add();
//		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);

		String selfId = request.getParameter("selfId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(selfId)){
			map.put("selfId", selfId);
		}
		if (comboTree.getId() != null) {
			//cq.isNull("TSPDepart");
			map.put("parentfunctionid", comboTree.getId());
		}
		List<TSFunction> functionList = functionService.getFunctionList(map);
		
		
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "functionName", "TSFunctions");
		for (Object obj : functionList) {
			comboTrees.add(comboTree(obj, comboTreeModel, null, false));
		}
		
		return comboTrees;
	}
	
	 /**
		 * 构建ComboTree
		 * @param obj
		 * @param comboTreeModel
		 * @param in
		 * @param recursive
		 * @return
		 */
		private ComboTree comboTree(Object obj, ComboTreeModel comboTreeModel, List<TSFunction> in, boolean recursive) {
			ComboTree tree = new ComboTree();
			Map<String, Object> attributes = new HashMap<String, Object>();
			ReflectHelper reflectHelper = new ReflectHelper(obj);
			String id = oConvertUtils.getString(reflectHelper.getMethodValue(comboTreeModel.getIdField()));
			tree.setId(id);
			tree.setText(oConvertUtils.getString(reflectHelper.getMethodValue(comboTreeModel.getTextField())));
			if (comboTreeModel.getSrcField() != null) {
				attributes.put("href", oConvertUtils.getString(reflectHelper.getMethodValue(comboTreeModel.getSrcField())));
				tree.setAttributes(attributes);
			}
			if (in == null) {
			} else {
				if (in.size() > 0) {
					for (TSFunction inobj : in) {
						ReflectHelper reflectHelper2 = new ReflectHelper(inobj);
						String inId = inobj.getId();
						if (inId.equals(id)) {
							tree.setChecked(true);
						}
					}
				}
			}
			int childList = functionService.getChildList((String)reflectHelper.getMethodValue(comboTreeModel.getIdField()));
			if (childList > 0) {
				tree.setState("closed");
				tree.setChecked(false);
				/*
				 * if (recursive) {// 递归查询子节点 List<TSFunction> functionList = new ArrayList<TSFunction>(tsFunctions); Collections.sort(functionList, new SetListSort());// 排序 List<ComboTree> children = new ArrayList<ComboTree>(); for (TSFunction f : functionList) { ComboTree t = comboTree(f,comboTreeModel,in, true); children.add(t); } tree.setChildren(children); }
				 */
			}
			return tree;
		}
}
