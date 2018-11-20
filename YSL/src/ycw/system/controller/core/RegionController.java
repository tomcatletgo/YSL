package ycw.system.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import core.util.StringUtil;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import tag.vo.datatable.SortDirection;
import tag.vo.easyui.TreeGridModel;
import ycw.system.pojo.base.TSRegion;
import ycw.system.pojo.base.TSTransformers;
import ycw.system.service.SystemService;


/**
 * 区域处理类
 * @author joe
 */
@Controller
@RequestMapping("/regionController")
public class RegionController extends BaseController {
	
	@Autowired
	private SystemService systemService;

	/**
	 * 地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "region")
	public ModelAndView regionList() {
		return new ModelAndView("system/region/regionList");
	}
	
	/**
	 * 弹出框地域列表和台区页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dgregion")
	public ModelAndView dgRegionList() {
		return new ModelAndView("system/region/dgRegionList");
	}
	
	/**
	 * 弹出框地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "selregion")
	public ModelAndView selRegionList() {
		return new ModelAndView("system/region/selRegionList");
	}
	
	/**
	 * 地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addregion")
	public ModelAndView addregion() {
		return new ModelAndView("system/region/region");
	}

	/**
	 * 地域列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateregion")
	public ModelAndView updateregion(HttpServletRequest request) {
		String regionid = oConvertUtils.getString(request.getParameter("id"));
		TSRegion tsRegion = systemService.get(TSRegion.class, regionid);
		request.setAttribute("region", tsRegion);
		return new ModelAndView("system/region/region");
	}
	
	/**
	 * 区域列表
	 * @param request
	 * @param treegrid
	 * @return List<TreeGrid>
	 */
	@RequestMapping(params = "regionGrid")
	@ResponseBody
	public List<TreeGrid> regionGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TSRegion.class);
		if (treegrid.getId() != null) {
			cq.eq("TSPRegion.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("TSPRegion");
		}
		cq.notEq("isDelete", "1");
		cq.addOrder("regionSort", SortDirection.asc);
		cq.add();
		List<TreeGrid> regionList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("regionName");
		treeGridModel.setParentText("TSPRegion_regionName");
		treeGridModel.setParentId("TSPRegion_id");
		treeGridModel.setSrc("regionCode");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("TSRegions");
		treeGridModel.setOrder("regionSort");
		treeGrids = systemService.treegrid(regionList, treeGridModel);
		return treeGrids;
	}
	
	/**
	 * 台区列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "transformersGrid")
	public void transformersGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String regionid = oConvertUtils.getString(request.getParameter("regionid"));
		
		CriteriaQuery cq = new CriteriaQuery(TSTransformers.class, dataGrid);
		if (!StringUtil.isEmpty(regionid)) {
			cq.eq("TSRegion.id", regionid);
		}
		cq.notEq("isDelete", "1");
		cq.add();
		
		this.systemService.getDataGridReturn(cq, true);
		
//		List<TSTransformers> transformerList = systemService.getListByCriteriaQuery(cq, false);
//		
//		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
//		Map<String, String> rowMap;
//		for (TSTransformers transformer : transformerList) {
//			rowMap = new HashMap<String, String>();
//			rowMap.put("id", transformer.getId());
//			rowMap.put("name", transformer.getTransformersName());
//			listMap.add(rowMap);
//		}
//		dataGrid.setResults(listMap);
//		
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 区域树
	 * @param request
	 * @param comboTree
	 * @return List<ComboTree>
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSRegion.class);
		String selfId = request.getParameter("selfId");
		if(!StringUtil.isEmpty(selfId)){
			cq.notEq("id", selfId);
		}
		if (comboTree.getId() != null) {
			cq.eq("TSPRegion.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSPRegion");
		}
		cq.eq("isDelete", "0");
		cq.add();
		List<TSRegion> regionsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
//		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");
//		comboTrees = systemService.ComboTree(departsList, comboTreeModel, null);
		
//		List<ComboTree> trees = new ArrayList<ComboTree>();
		for (TSRegion region : regionsList) {
			comboTrees.add(tree(region, selfId, true));
		}
		return comboTrees;

	}
	
	/**
	 * 区域树构造
	 * @param region
	 * @param selfId
	 * @param recursive
	 * @return List<ComboTree>
	 */
	public ComboTree tree(TSRegion region, String selfId, boolean recursive) {
		ComboTree tree = new ComboTree();
		tree.setId(oConvertUtils.getString(region.getId()));
		tree.setText(region.getRegionName());
		//List<TSRegion> regionsList = systemService.findByProperty(TSRegion.class, "TSPRegion.id", region.getId());
		
		CriteriaQuery cq = new CriteriaQuery(TSRegion.class);
		cq.eq("TSPRegion.id", region.getId());
		cq.eq("isDelete", "0");
		cq.add();
		List<TSRegion> regionsList = systemService.getListByCriteriaQuery(cq, true);
		
		if (regionsList != null && regionsList.size() > 0) {
			if(regionsList.size()==1 && selfId.equals(regionsList.get(0).getId())){
				return tree;
			}
			tree.setState("closed");
			tree.setChecked(false);
			if (recursive) {// 递归查询子节点
				List<TSRegion> regionList = new ArrayList<TSRegion>(regionsList);
				//Collections.sort(regionList, new SetListSort());// 排序
				List<ComboTree> children = new ArrayList<ComboTree>();
				for (TSRegion d : regionList) {
					if(!selfId.equals(d.getId())){
						ComboTree t = tree(d,selfId, true);
						children.add(t);
					}			
				}
				tree.setChildren(children);
			}
		}
		return tree;
	}
	
	/**
	 * 区域录入
	 * 
	 * @param region
	 * @param request
	 */
	@RequestMapping(params = "saveRegion")
	@ResponseBody
	public AjaxJson saveRegion(TSRegion region, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = null;
		if(StringUtils.isEmpty(region.getRegionSort())){
			region.setRegionSort("0");
		}
		if (region.getRegionLevel()==0||region.getTSPRegion().getId().equals("")) {
			region.setTSPRegion(null);
		}else{
			TSRegion parent = systemService.getEntity(TSRegion.class, region.getTSPRegion().getId());
			region.setRegionLevel(Short.valueOf(parent.getRegionLevel()+1+""));
		}
		region.setIsDelete("0");
		if (StringUtil.isNotEmpty(region.getId())) {
			message = "区域: " + region.getRegionName() + "被更新成功";
			systemService.saveOrUpdate(region);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "区域: " + region.getRegionName() + "被添加成功";
			systemService.save(region);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 区域删除
	 * 
	 * @param region
	 * @param request
	 */
	@RequestMapping(params = "delregion")
	@ResponseBody
	public AjaxJson delregion(TSRegion region, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = null;
		
		CriteriaQuery cq = new CriteriaQuery(TSTransformers.class);
		cq.eq("TSRegion.id", region.getId());
		cq.eq("isDelete", "0");
		cq.add();
		List<TSTransformers> tsTransformers = systemService.getListByCriteriaQuery(cq, true);
		
		if(tsTransformers.size() > 0){
			message = "请先清空该区域中的台区！";
			j.setMsg(message);
		} else {
			region = systemService.getEntity(TSRegion.class, region.getId());
			region.setIsDelete("1");
			systemService.saveOrUpdate(region);
			message = "区域: " + region.getRegionName() + "被删除成功";
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}
		return j;
	}
	
	/**
	 * 地域列表页面跳转
	 * 
	 * @param request
	 * @param regionid
	 * @return
	 */
	@RequestMapping(params = "transformersList")
	public ModelAndView transformers(HttpServletRequest request, String regionid) {
		request.setAttribute("regionid", regionid);
		return new ModelAndView("system/region/transformersList");
	}
	
	/**
	 * 地域列表页面跳转
	 * 
	 * @param request
	 * @param regionid
	 * @return
	 */
	@RequestMapping(params = "dgtransformersList")
	public ModelAndView dgtransformers(HttpServletRequest request, String regionid) {
		request.setAttribute("regionid", regionid);
		return new ModelAndView("system/region/dgTransformersList");
	}
	
	/**
	 * 地域列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addtransformers")
	public ModelAndView addtransformers(HttpServletRequest request) {
		String regionid = oConvertUtils.getString(request.getParameter("regionid"));
		request.setAttribute("regionid", regionid);
		return new ModelAndView("system/region/transformers");
	}

	/**
	 * 地域列表页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updatetransformers")
	public ModelAndView updatetransformers(HttpServletRequest request) {
		String regionid = oConvertUtils.getString(request.getParameter("regionid"));
		request.setAttribute("regionid", regionid);
		String transformersid = oConvertUtils.getString(request.getParameter("id"));
		TSTransformers tsTransformers = systemService.get(TSTransformers.class, transformersid);
		request.setAttribute("transformers", tsTransformers);
		return new ModelAndView("system/region/transformers");
	}
	
	/**
	 * 台区录入
	 * 
	 * @param transformers
	 * @param request
	 */
	@RequestMapping(params = "saveTransformers")
	@ResponseBody
	public AjaxJson saveTransformers(TSTransformers transformers, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = null;
		transformers.setIsDelete("0");
		if (StringUtil.isNotEmpty(transformers.getId())) {
			message = "台区: " + transformers.getTransformersName() + "被更新成功";
			systemService.saveOrUpdate(transformers);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "台区: " + transformers.getTransformersName() + "被添加成功";
			systemService.save(transformers);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 台区删除
	 * 
	 * @param transformers
	 * @param request
	 */
	@RequestMapping(params = "deltransformers")
	@ResponseBody
	public AjaxJson del(TSTransformers transformers, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
	    String message = null;
		transformers = systemService.getEntity(TSTransformers.class, transformers.getId());
		message = "台区: " + transformers.getTransformersName() + "被删除成功";
		transformers.setIsDelete("1");
		systemService.saveOrUpdate(transformers);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		return j;
	}
}
