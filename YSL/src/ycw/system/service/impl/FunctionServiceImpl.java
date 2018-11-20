package ycw.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import core.common.model.json.TreeGrid;
import core.common.service.impl.CommonServiceImpl;
import core.util.ReflectHelper;
import core.util.oConvertUtils;
import tag.core.easyui.TagUtil;
import tag.vo.easyui.TreeGridModel;
import ycw.system.dao.FunctionDao;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSIcon;
import ycw.system.pojo.base.TSOperation;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.service.FunctionService;
import ycw.system.vo.FunctionVo;

@Service("functionService")
public class FunctionServiceImpl extends CommonServiceImpl implements FunctionService{

	public FunctionDao functionDao = null;
	
	@Resource
	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}
	

	/***
	 * 遍历菜单的集合
	 * @param map
	 * @return
	 */
	public <T> List<T> getFunctionList(Map<String, Object> map){
		
		return functionDao.getFunctionList(map);
	}
	

	

	/***
	 * 
	 * @param all
	 * @param treeGridModel
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
				String iconpath = functionDao.getIconPath(icon);
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
			int childList = functionDao.getChildList((String)reflectHelper.getMethodValue(treeGridModel.getIdField()));

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

			treegrid.add(tg);
		}
		return treegrid;
	}

	/***
	 * 返回TSFunction集合
	 * @return
	 */
	public <T> List<T> getAllList(){
		return functionDao.getAllList();
	}
	
	/***
	 * 返回TSIcon集合
	 * @return
	 */
	public <T> List<T> getIconList(){
		return functionDao.getIconList();
	}
	
	/***
	 * 根据id返回VO类对象
	 * @param id
	 * @return
	 */
	public FunctionVo getById(String id){
		return functionDao.getById(id);
		
	}

	/***
	 * 通过主菜单parentId返回主菜单对象
	 * @param parentId
	 * @return
	 */
	public TSFunction getParentFunction(String parentId){

		return functionDao.getParentFunction(parentId);
	}
	
	/***
	 * 保存操作
	 * @param function
	 */
	public void saveFunction(TSFunction function){
		functionDao.saveFunction(function);
	}
	
	/***
	 * 更新操作
	 * @param function
	 */
	public void updateFunction(TSFunction function){
		functionDao.updateFunction(function);
	}
	
	/***
	 * 通过主键返回对象
	 * @param id
	 * @return
	 */
	public TSIcon getIconById(String id){
		return functionDao.getIconById(id);
	}
	
	/***
	 * 删除子菜单
	 * @param id
	 */
	public void deleteChildFunction(String id){
		functionDao.deleteChildFunction(id);
	}
	
	/***
	 * 删除主菜单
	 * @param id
	 */
	public void deleteFunction(String id){
		functionDao.deleteFunction(id);
	}
	

	/***
	 * 根据id返回子菜单
	 * @param parentId
	 * @return
	 */
	public int getChildList(String parentId){
		return functionDao.getChildList(parentId);
	}
}
