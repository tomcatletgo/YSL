package ycw.system.service;

import java.util.List;
import java.util.Map;

import core.common.model.json.TreeGrid;
import core.common.service.CommonService;
import tag.vo.easyui.TreeGridModel;
import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSIcon;
import ycw.system.vo.FunctionVo;

public interface FunctionService extends CommonService{

	/***
	 * 遍历菜单的集合
	 * @param map
	 * @return
	 */
	public <T> List<T> getFunctionList(Map<String, Object> map);
	/***
	 * 
	 * @param all
	 * @param treeGridModel
	 */
	public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel);
	/***
	 * 返回TSFunction集合
	 * @return
	 */
	public <T> List<T> getAllList();
	/***
	 * 返回TSIcon集合
	 * @return
	 */
	public <T> List<T> getIconList();
	/***
	 * 根据id返回VO类对象
	 * @param id
	 * @return
	 */
	public FunctionVo getById(String id);
	/***
	 * 通过主菜单parentId返回主菜单对象
	 * @param parentId
	 * @return
	 */
	public TSFunction getParentFunction(String parentId);
	/***
	 * 保存操作
	 * @param function
	 */
	public void saveFunction(TSFunction function);
	/***
	 * 更新操作
	 * @param function
	 */
	public void updateFunction(TSFunction function);
	/***
	 * 通过主键返回对象
	 * @param id
	 * @return
	 */
	public TSIcon getIconById(String id);
	/***
	 * 删除子菜单
	 * @param id
	 */
	public void deleteChildFunction(String id);
	/***
	 * 删除主菜单
	 * @param id
	 */
	public void deleteFunction(String id);
	
	/***
	 * 根据id返回子菜单
	 * @param parentId
	 * @return
	 */
	public int getChildList(String parentId);
	
}
