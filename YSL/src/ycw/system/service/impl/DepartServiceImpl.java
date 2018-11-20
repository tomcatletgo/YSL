package ycw.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.common.model.json.TreeGrid;
import core.common.service.impl.CommonServiceImpl;
import core.util.ReflectHelper;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import tag.vo.easyui.TreeGridModel;
import ycw.system.dao.DepartDao;
import ycw.system.pojo.base.TSDepart;
import ycw.system.pojo.base.TSOperation;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.service.DepartService;
import ycw.system.vo.DepartVo;

@Service("departService")
@Transactional
public class DepartServiceImpl extends CommonServiceImpl implements DepartService{

	public DepartDao departDao = null;
	
	
	@Resource
	public void setDepartDao(DepartDao departDao) {
		this.departDao = departDao;
	}
	

	/**
	 * 查询部门机构表
	 * @param map
	 */
	public <T> List<T> getDepartList(Map<String, Object> map){
		
		return departDao.getDepartList(map);
	}
	

	
	/**
	 * 树形菜单
	 * @param list all
	 * @param TreeGridModel treeGridModel
	 */

	public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel) {
		List<TreeGrid> treegrid = new ArrayList<TreeGrid>();
		for (Object obj : all) {
			ReflectHelper reflectHelper = new ReflectHelper(obj);
			TreeGrid tg = new TreeGrid();
			String id = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getIdField()));
			String src = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getSrc()));
			String text = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getTextField()));
			if(StringUtils.isNotEmpty(treeGridModel.getOrder())){
				String order = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getOrder()));
				tg.setOrder(order);
			}
			tg.setId(id);
			
			String icon = (String)reflectHelper.getMethodValue(treeGridModel.getIcon());
			if (icon != null) {
				String iconpath = departDao.getIconPath(icon);
				if (iconpath != null) {
					tg.setCode(iconpath);
				} else {
					tg.setCode("");
				}
			}
			tg.setSrc(src);
			tg.setText(text);
			if (treeGridModel.getParentId() != null) {
				Object pid = TagUtil.fieldNametoValues(treeGridModel.getParentId(), obj);
				if (pid != null) {
					tg.setParentId(pid.toString());
				} else {
					tg.setParentId("");
				}
			}
			if (treeGridModel.getParentText() != null) {
				Object ptext = TagUtil.fieldNametoValues(treeGridModel.getTextField(), obj);
				if (ptext != null) {
					tg.setParentText(ptext.toString());
				} else {
					tg.setParentText("");
				}

			}
			int childList = departDao.getChildList((String)reflectHelper.getMethodValue(treeGridModel.getIdField()));

			if (childList > 0) {
				tg.setState("closed");
			}
			if (treeGridModel.getRoleid() != null) {
				String[] opStrings = {};
				List<TSRoleFunction> roleFunctions = findByProperty(TSRoleFunction.class, "TSFunction.id", id);

				if (roleFunctions.size() > 0) {
					for (TSRoleFunction tRoleFunction : roleFunctions) {
						TSRoleFunction roleFunction = tRoleFunction;
						if (roleFunction.getTSRole().getId().toString().equals(treeGridModel.getRoleid())) {
							String bbString = roleFunction.getOperation();
							if (bbString != null) {
								opStrings = bbString.split(",");
								break;
							}
						}
					}
				}
				List<TSOperation> operateions = findByProperty(TSOperation.class, "TSFunction.id", id);
				StringBuffer attributes = new StringBuffer();
				if (operateions.size() > 0) {
					for (TSOperation tOperation : operateions) {
						if (opStrings.length < 1) {
							attributes.append("<input type=checkbox name=operatons value=" + tOperation.getId() + "_" + id + ">" + tOperation.getOperationname());
						} else {
							StringBuffer sb = new StringBuffer();
							sb.append("<input type=checkbox name=operatons");
							for (int i = 0; i < opStrings.length; i++) {
								if (opStrings[i].equals(tOperation.getId().toString())) {
									sb.append(" checked=checked");
								}
							}
							sb.append(" value=" + tOperation.getId() + "_" + id + ">" + tOperation.getOperationname());
							attributes.append(sb.toString());
						}
					}
				}
				tg.setOperations(attributes.toString());
			}
			//显示code
			//String code = oConvertUtils.getString(reflectHelper.getMethodValue(treeGridModel.getCode()));
			//tg.setCode(code);
			treegrid.add(tg);
		}
		return treegrid;
	}
	/**
	 * 查询t_s_depart
	 */
	@Override
	public <T> List<T> getList() {
		// TODO Auto-generated method stub
		return departDao.getList();
	}
	
	/**
	 * 查询t_s_depart
	 */
	public DepartVo getById(String id){
		return departDao.getById(id);
		
	}
	
	/**
	 * 查询t_s_depart
	 * @param map
	 */
	public <T> List<T> getComboTree(Map<String, Object> map){
		return departDao.getComboTree(map);
	}
	

	/**
	 * 查询t_s_depart（部门一致性）
	 * @param map
	 */
	public <T> List<T> checkDepartName(Map<String, Object> map){
		return departDao.checkDepartName(map);
	}
	
	/**
	 * 查询t_s_depart（部门code一致性）
	 * @param map
	 */
	public <T> List<T> checkDepartCode(Map<String, Object> map){
		return departDao.checkDepartCode(map);
	}
	
	/**
	 * 添加部门
	 * @param map
	 */
	public void save(Map<String, Object> map){
		departDao.save(map);
	}
	
	/**
	 * 修改部门信息
	 * @param DepartVo depart
	 */
	public void update(DepartVo depart){
		departDao.update(depart);
	}
	/**
	 * 查询t_s_depart（获取子部门Id）
	 * @param String parentId
	 */
	@Override
	public <T> List<T> getChildDepart(String parentId) {
		return departDao.getChildDepart(parentId);
	}
	
	/**
	 * 查询t_s_base_user（部门相关用户信息）
	 * @param String id
	 */
	public <T> List<T> getUserList(String id){
		return departDao.getUserList(id);
	}
	/**
	 * 查询t_b_device（相关终端信息）
	 * @param String id
	 */
	public <T> List<T> getDevice(String id){
		return departDao.getDevice(id);
	}
	/**
	 * 查询t_b_sim（相关SIM卡信息）
	 * @param String id
	 */
	public <T> List<T> getSimList(String id){
		return departDao.getSimList(id);
	}
	/**
	 * 查询t_b_ammeter（相关电表信息）
	 * @param String id
	 */
	public <T> List<T> getAmmeter(String id){
		return departDao.getAmmeter(id);
	}
	/**
	 * 查询t_b_ammeterbox（相关电表信息）
	 * @param String id
	 */
	public <T> List<T> getAmmeterBox(String id){
		return departDao.getAmmeterBox(id);
	}
	/**
	 * 查询t_b_collector（相关采集器信息）
	 * @param String id
	 */
	public <T> List<T> getCollector(String id){
		return departDao.getCollector(id);
	}
	/**
	 * 查询t_b_concentrator（相关集中器信息）
	 * @param String id
	 */
	public <T> List<T> getConcentrator(String id){
		return departDao.getConcentrator(id);
	}
	/**
	 * 删除
	 * @param String id
	 */
	public void deleteDepart(String id){
		departDao.deleteDepart(id);
	}
	
	/**
	 * 查询公司
	 * @param name 部门id
	 * @return 
	 */
	public TSDepart getDepart(Map<String,Object> map){
		return	departDao.getDepart(map);
	}
	
	
}
