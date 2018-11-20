package ycw.system.controller.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import core.common.controller.BaseController;
import core.common.hibernate.qbc.CriteriaQuery;
import core.common.model.json.AjaxJson;
import core.common.model.json.ComboBox;
import core.common.model.json.ComboTree;
import core.common.model.json.DataGrid;
import core.common.model.json.TreeGrid;
import core.common.model.json.ValidForm;
import core.constant.Globals;
import core.util.ExceptionUtil;
import core.util.ReflectHelper;
import core.util.StringUtil;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import tag.vo.easyui.ComboTreeModel;
import tag.vo.easyui.TreeGridModel;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSOperation;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.service.RoleService;
import ycw.system.service.SystemService;
import ycw.system.service.UserService;
import ycw.system.vo.RoleFunctionVo;


/**
 * 角色处理类
 * 
 * @author 张代浩
 * 
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoleController.class);
	private UserService userService;
	private SystemService systemService;
	private RoleService roleService;
	private String message = null;
	

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

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	public ModelAndView role() {
		return new ModelAndView("system/role/roleList");
	}
	
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * easyuiAJAX请求数据
	 * @param role
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "roleGrid")
	public void roleGrid( TSRole role,HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		int page = dataGrid.getPage();
		int rows = dataGrid.getRows();
		

		map.put("begin", page * rows - rows);
		map.put("end", rows);
		
		List<TSRoleFunction> data = this.roleService.getRoleList(map);
		
		dataGrid.setResults(data);

		int count = this.roleService.getRoleCount(map);
		dataGrid.setTotal(count);
		TagUtil.datagrid(response, dataGrid);
//		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
//		core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, role);
//		cq.add();
//		this.systemService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);;
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delRole")
	@ResponseBody
	public AjaxJson delRole(TSRole role,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		int num = 0;
		String message = null;
		if(StringUtil.isNotEmpty(role.getId())) {
			//查询人员关系表
			if(roleService.selecRoUsCount(role.getId())>0){
				message = "角色: 仍被用户使用，请先删除关联关系";
				j.setMsg(message);
			}else
			{
				//删除关系表
				try {
					delRoleFunction(role);
					num = 1;
				} catch (Exception e) {
					num = 0;
				}
				//删除主表
				//roleService.deletRole(role);
				//message = "角色: " + role.getRoleName() + "被删除成功";
				//systemService.addLog(message, Globals.Log_Type_DEL,Globals.Log_Leavel_INFO);
			}
		}else{
			message = "删除未成功";
			j.setMsg(message);
		}
		j.setObj(num);
		return j;
	}
	
	/**
	 * 删除角色
	 * 
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delRoleFun")
	@ResponseBody
	public AjaxJson delRoleFun(TSRole role,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = null;
		if(StringUtil.isNotEmpty(role.getId())) {
			//查询人员关系表
			if(roleService.selecRoUsCount(role.getId())>0){
				message = "角色: 仍被用户使用，请先删除关联关系";
				j.setMsg(message);
			}else
			{
				//删除主表
				roleService.deletRole(role);
				message = "角色: " + role.getRoleName() + "被删除成功";
				systemService.addLog(message, Globals.Log_Type_DEL,Globals.Log_Leavel_INFO);
				message = "删除成功";
				j.setMsg(message);
			}
		}else{
			message = "删除未成功";
			j.setMsg(message);
		}
		return j;
	}
	
	/**
	 * 检查角色
	 * 
	 * @param role
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "checkRole")
	@ResponseBody
	public ValidForm checkRole(TSRole role,HttpServletRequest request,HttpServletResponse response) {
		ValidForm v = new ValidForm();
		String roleCode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", roleCode);
//		List<TSRole> roles=roleService.selectCheckRole(role);
		List<Object> roles=roleService.selectCheckRole(map);
//		List<TSRole> roles=systemService.findByProperty(TSRole.class,"roleCode",roleCode);
		if(roles.size()>0&&!code.equals(roleCode))
		{
			v.setInfo("角色编码已存在");
			v.setStatus("n");
		}
		return v;
	}
	
	/**
	 * 删除角色权限
	 * 
	 * @param role
	 */
	protected void delRoleFunction(TSRole role){
//		List<TSRoleFunction> roleFunctions=systemService.findByProperty(
//				TSRoleFunction.class,"TSRole.id",role.getId());	
//		List<TSRoleFunction> roleFunctions = roleService.selectRoleid(role);
		
//		if (roleFunctions.size() > 0) {
//			for (TSRoleFunction tsRoleFunction : roleFunctions) {
//			systemService.delete(tsRoleFunction);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", role.getId());
			roleService.deletRoleFunction(map);
	//		}
	//	}
	//	List<TSRoleUser> roleUsers=systemService.findByProperty(TSRoleUser.class,"TSRole.id",role.getId());
	    
//		List<TSRoleUser> roleUsers=roleService.selectroleUsers(role);
//		for (TSRoleUser tsRoleUser : roleUsers) {
	//    	systemService.delete(tsRoleUser);
//			roleService.deletRoleUser(tsRoleUser);
//		}
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
//		String id = request.getParameter("id");
//		String roleCode = request.getParameter("roleCode");
//		String roleName = request.getParameter("roleName");
		if (StringUtil.isNotEmpty(role.getId())) {
			message = "角色: " + role.getRoleName() + "被更新成功";
			
			roleService.updateRole(role);
//			userService.saveOrUpdate(role);
//			
//			String Id = role.getRoleName();
			
//			Group group = identityService.createGroupQuery().groupId(role.getId()).singleResult();
//			group.setName(role.getRoleName());
//			group.setType(role.getRoleCode());
//			identityService.saveGroup(group);
//			
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} else {
			message = "角色: " + role.getRoleName() + "被添加成功";
//			type.setId(UUID.randomUUID().toString().replaceAll("-",""));
			role.setId(UUID.randomUUID().toString().replaceAll("-",""));
			roleService.saveRole(role);
//			userService.save(role);
//			Group group = identityService.newGroup(role.getId());
//			group.setName(role.getRoleName());
//			group.setType(role.getRoleCode());
//			identityService.saveGroup(group);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		return j;
	}

	/**
	 * 角色列表页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "fun")
	public ModelAndView fun(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		return new ModelAndView("system/role/roleSet");
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
	public List<ComboTree> setAuthority(TSRole role, HttpServletRequest request,
			ComboTree comboTree) {
//		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
//		
//		if (comboTree.getId() != null) {
//			cq.eq("TSFunction.id", comboTree.getId());
//		}
//		if (comboTree.getId() == null) {
//			cq.isNull("TSFunction");
//		}
//		cq.notEq("functionLevel",Short.parseShort("-1"));
//		cq.add();
//		System.out.println("================================");
//		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq,false);
//
//		System.out.println("================================");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (comboTree.getId() != null) {
			map.put("parentId", comboTree.getId());
		}
		
		map.put("functionLevel", -1);
		List<TSFunction> functionList = roleService.getFunctionlist(map);
		
		
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String roleId = request.getParameter("roleId");
		List<TSFunction> loginActionlist = new ArrayList<TSFunction>();// 已有权限菜单
		role = roleService.selectRole(roleId);
		
		
		if (role != null) {
//			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(
//					TSRoleFunction.class, "TSRole.id", role.getId());

			List<TSFunction> roleFunctionList = roleService.getRoleFun(role.getId());
			loginActionlist.addAll(roleFunctionList);
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "functionName", "TSFunctions");
		for (Object obj : functionList) {
			comboTrees.add(comboTree(obj,comboTreeModel,loginActionlist, false));
		}
		
		return comboTrees;
	}

	/**
	 * 更新权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateAuthority")
	@ResponseBody
	public AjaxJson updateAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			String roleId =request.getParameter("roleId");
			String rolefunction = request.getParameter("rolefunctions");
			TSRole role = roleService.selectRole(roleId);
//			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(
//					TSRoleFunction.class, "TSRole.id", role.getId());
			
			List<RoleFunctionVo> roleFunctionList = roleService.getrf(role.getId());
			
			Map<String,RoleFunctionVo> map = new HashMap<String,RoleFunctionVo>();
			for (RoleFunctionVo functionOfRole : roleFunctionList){
				map.put(functionOfRole.getFunctionid(),functionOfRole);
			}
			String[] roleFunctions = rolefunction.split(",");
			Set<String> set = new HashSet<String>();
			for (String s : roleFunctions) {
				set.add(s);
			}
			updateCompare(set,role,map);
			j.setMsg("权限更新成功");			
		}catch (Exception e){
            logger.error(ExceptionUtil.getExceptionMessage(e));    
			j.setMsg("权限更新失败");			
		}
		return j;
	}
	
	/**
	 * 权限比较
	 * @param set 最新的权限列表
	 * @param role 当前角色
	 * @param map 旧的权限列表
	 */
	private void updateCompare(Set<String> set,TSRole role,
			Map<String, RoleFunctionVo> map) {
		List<RoleFunctionVo> entitys = new ArrayList<RoleFunctionVo>();
		List<RoleFunctionVo> deleteEntitys = new ArrayList<RoleFunctionVo>();
		for (String s : set) {
			if(map.containsKey(s)){
				map.remove(s);
			}else{
				RoleFunctionVo rf = new RoleFunctionVo();
//				TSFunction f = this.systemService.get(TSFunction.class,s);
				rf.setFunctionid(s);
				rf.setRoleid(role.getId());
				rf.setId(UUID.randomUUID().toString().replaceAll("-",""));
				entitys.add(rf);
			}
		}
		Collection<RoleFunctionVo> collection = map.values();
		Iterator<RoleFunctionVo> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
	    }
		if(entitys.size() > 0)
			roleService.saveList(entitys);
		if(deleteEntitys.size() > 0)
			roleService.deleteAll(deleteEntitys);
		
	}

	/**
	 * 角色页面跳转
	 * 
	 * @param role
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSRole role, HttpServletRequest req) {
		if (role.getId() != null) {
			String id = req.getParameter("id");
//			selectRoleVo = roleService.selectRole(role);
			TSRole rol = roleService.selectRole(id);
//			role = systemService.getEntity(TSRole.class, role.getId());
			req.setAttribute("role", rol);
		}
		return new ModelAndView("system/role/role");
	}

	/**
	 * 权限操作列表
	 * 
	 * @param request
	 * @param treegrid
	 * @return List<TreeGrid>
	 */
	@RequestMapping(params = "setOperate")
	@ResponseBody
	public List<TreeGrid> setOperate(HttpServletRequest request,
			TreeGrid treegrid) {
		String roleId = request.getParameter("roleId");
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (treegrid.getId() != null) {
			cq.eq("TSFunction.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("TSFunction");
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq,false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
//		Collections.sort(functionList, new SetListSort());
		TreeGridModel treeGridModel=new TreeGridModel();
		treeGridModel.setRoleid(roleId);
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
		String roleId = request.getParameter("roleId");
		//录入操作前清空上一次的操作数据
		clearp(roleId);
		String[] fun_op = fop.split(",");
		String aa = "";
		String bb = "";
		//只有一个被选中
		if (fun_op.length == 1) {
			bb = fun_op[0].split("_")[1];
			aa = fun_op[0].split("_")[0];
			savep( roleId,bb,aa);
		}else{
			//至少2个被选中
			for (int i = 0; i < fun_op.length; i++) {
				String cc = fun_op[i].split("_")[0];  //操作id
				if (i > 0 && bb.equals(fun_op[i].split("_")[1])) {
					aa += "," + cc;
					if (i== (fun_op.length - 1)) {
						savep( roleId,bb,aa);
					}
				} else if (i > 0) {
					    savep(roleId,bb,aa);
					    aa = fun_op[i].split("_")[0];   //操作ID
					    if (i==(fun_op.length-1)){
					    	bb = fun_op[i].split("_")[1]; //权限id
					    	savep(roleId,bb,aa);
						}
					    
				} else {
					aa = fun_op[i].split("_")[0]; //操作ID
				}
				bb = fun_op[i].split("_")[1]; //权限id
			}	
		}
		return j;
	}
	
     /**
      * 更新操作
      * @param roleId
      * @param functionid
      * @param ids
      */
	public void savep(String roleId, String functionid, String ids) {
		//String hql = "from TSRoleFunction t where" + " t.TSRole.id=" + oConvertUtils.getInt(roleId,0)
		//		+ " " + "and t.TSFunction.id=" + oConvertUtils.getInt(functionid,0);		
		CriteriaQuery cq=new CriteriaQuery(TSRoleFunction.class);
		cq.eq("TSRole.id",roleId);
		cq.eq("TSFunction.id",functionid);
		cq.add();
		List<TSRoleFunction> rFunctions =systemService.getListByCriteriaQuery(cq,false);
		if (rFunctions.size() > 0) {
			TSRoleFunction roleFunction = rFunctions.get(0);
			roleFunction.setOperation(ids);
			systemService.saveOrUpdate(roleFunction);
		}
	}
	
	/**
	 * 清空操作
	 * @param roleId
	 */
	public void clearp(String roleId) {	
		List<TSRoleFunction> rFunctions = systemService.findByProperty(
				TSRoleFunction.class,"TSRole.id",roleId);
		if (rFunctions.size() > 0){
			for (TSRoleFunction tRoleFunction : rFunctions) {
				tRoleFunction.setOperation(null);
				systemService.saveOrUpdate(tRoleFunction);
			}
	 	}
	}
	
	/**
	 * 按钮权限展示
	 * @param request
	 * @param functionId
	 * @param roleId
	 * @return
	 */
	@RequestMapping(params = "operationListForFunction")
	public ModelAndView operationListForFunction(HttpServletRequest request, 
			String functionId, String roleId) {	
		CriteriaQuery cq = new CriteriaQuery(TSOperation.class);
		cq.eq("TSFunction.id", functionId);
		cq.add(); 
		List<TSOperation> operationList = this.systemService.getListByCriteriaQuery(cq, false);
		Set<String> operationCodes = systemService.getOperationCodesByRoleIdAndFunctionId(roleId, functionId);
		request.setAttribute("operationList", operationList);
		request.setAttribute("operationcodes", operationCodes);
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/role/operationListForFunction");
	}
	
	/**
	 * 更新按钮权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateOperation")
	@ResponseBody
	public AjaxJson updateOperation(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String roleId =request.getParameter("roleId");
		String functionId = request.getParameter("functionId");
		String operationcodes = request.getParameter("operationcodes");
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id",roleId);
		cq1.eq("TSFunction.id",functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions =systemService.getListByCriteriaQuery(cq1,false);
		if(null!=rFunctions && rFunctions.size()>0){
			TSRoleFunction tsRoleFunction =  rFunctions.get(0);
			tsRoleFunction.setOperation(operationcodes);
			systemService.saveOrUpdate(tsRoleFunction);
		}
		j.setMsg("按钮权限更新成功");
		return j;
	}
	
    /**
     * 得到角色下拉列表
     * 
     * @param response
     * @param request
     * @param comboBox
     * @return List<ComboBox> 下拉框对象集合
     */
    @RequestMapping(params = "setRoleType")
    @ResponseBody
    public List<ComboBox> setRoleType(HttpServletResponse response,
            HttpServletRequest request, ComboBox comboBox) {
        comboBox.setId("id");
        comboBox.setText("rolename");
        List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
        List<TSRole> roles = new ArrayList<TSRole>();

        CriteriaQuery cq = new CriteriaQuery(TSRole.class);
        List<TSRole> roleList = systemService.getListByCriteriaQuery(cq, false);

        comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);
        return comboBoxs;
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
		List tsFunctions = (List) roleService.getChildFunlist(id);
		if (tsFunctions != null && tsFunctions.size() > 0) {
			tree.setState("closed");
			tree.setChecked(false);
			/*
			 * if (recursive) {// 递归查询子节点 List<TSFunction> functionList = new ArrayList<TSFunction>(tsFunctions); Collections.sort(functionList, new SetListSort());// 排序 List<ComboTree> children = new ArrayList<ComboTree>(); for (TSFunction f : functionList) { ComboTree t = comboTree(f,comboTreeModel,in, true); children.add(t); } tree.setChildren(children); }
			 */
		}
		return tree;
	}
}
